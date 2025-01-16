package com.brycehan.boot.framework.common.utils;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.brycehan.boot.common.constant.CacheConstants.CACHE_NULL_TTL;
import static com.brycehan.boot.common.constant.CacheConstants.LOCK_KEY;

/**
 * 缓存客户端封装
 *
 * @author Bryce Han
 * @since 2024/8/21
 */
@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CacheClient {

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    /**
     * 设置缓存
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.writeValueAsString(value), timeout, unit);
    }

    /**
     * 设置逻辑过期缓存
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public void setWithLogicalExpire(String key, Object value, long timeout, TimeUnit unit) {
        // 设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(timeout)));
        // 写入Redis
        stringRedisTemplate.opsForValue().set(key, JsonUtils.writeValueAsString(redisData));
    }

    /**
     * 查询并缓存，通过逻辑过期解决缓存击穿问题
     *
     * @param keyPrefix  缓存前缀
     * @param id         主键
     * @param entityType 实体类型
     * @param dbFunction 数据库查询函数
     * @param timeout    超时时间
     * @param unit       时间单位
     * @param <E>        实体类型
     * @param <ID>       主键类型
     * @return 实体
     */
    public <E, ID> E queryWithLogicalExpire(String keyPrefix, ID id, Class<E> entityType, Function<ID, E> dbFunction, long timeout, TimeUnit unit) {
        String key = keyPrefix + id;
        // 从Redis查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 判断缓存是否存在
        if (StrUtil.isBlank(json)) {
            return null;
        }

        // 命中，需要先把json反序列化为对象
        RedisData redisData = JsonUtils.readValue(json, RedisData.class);

        if (redisData == null) {
            return null;
        }

        E entity = JsonUtils.convertValue(redisData.getData(), entityType);
        LocalDateTime expireTime = redisData.getExpireTime();

        // 判断是否过期
        if (expireTime.isAfter(LocalDateTime.now())) {
            // 未过期，直接返回
            return entity;
        }

        // 已过期，需要缓存重建
        String lockKey = LOCK_KEY + entityType.getSimpleName() + ":" + id;
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock()) {
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    //
                    // 查询数据库
                    E newEntity = dbFunction.apply(id);
                    // 重建缓存
                    setWithLogicalExpire(key, newEntity, timeout, unit);
                } catch (Exception e) {
                    log.error("缓存重建异常：{}", e.getMessage());
                } finally {
                    lock.unlock();
                }
            });
        }

        // 返回过期的缓存数据
        return entity;
    }

    /**
     * 查询并缓存<br>
     * 通过互斥锁解决缓存击穿问题；通过缓存空值解决缓存穿透问题
     *
     * @param keyPrefix  缓存前缀
     * @param id         主键
     * @param entityType 实体类型
     * @param dbFunction 数据库查询函数
     * @param timeout    超时时间
     * @param unit       时间单位
     * @param <E>        实体类型
     * @param <ID>       主键类型
     * @return 实体
     */
    public <E, ID> E queryWithMutex(String keyPrefix, ID id, Class<E> entityType, Function<ID, E> dbFunction, long timeout, TimeUnit unit) {
        String key = keyPrefix + id;
        // 从Redis查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);

        // 判断缓存是否存在
        if (StrUtil.isNotBlank(json)) {
            return JsonUtils.readValue(json, entityType);
        }

        // 判断是否是空值
        if (json != null) {
            return null;
        }

        // 需要缓存重建
        String lockKey = LOCK_KEY + entityType.getSimpleName() + ":" + id;
        E entity;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            // 获取互斥锁
            if (!lock.tryLock()) {
                // 获取锁失败，休眠并重试
                Thread.sleep(50);
                return queryWithMutex(keyPrefix, id, entityType, dbFunction, timeout, unit);
            }

            // 获取锁成功，查询数据库
            entity = dbFunction.apply(id);
            if (entity == null) {
                // 不存在，写入空值
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }

            // 存在，写入Redis
            set(key, entity, timeout, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        // 返回过期的缓存数据
        return entity;
    }

}

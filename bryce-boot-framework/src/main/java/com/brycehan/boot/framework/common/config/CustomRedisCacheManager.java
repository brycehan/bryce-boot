package com.brycehan.boot.framework.common.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 自定义Redis缓存管理
 *
 * @author Bryce Han
 * @since 2024/3/29
 */
public class CustomRedisCacheManager extends RedisCacheManager {

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * 创建缓存
     *
     * @param name {@link String name} for the {@link RedisCache}; must not be {@literal null}.
     * @param cacheConfiguration {@link RedisCacheConfiguration} used to configure the {@link RedisCache};
     * resolves to the {@link #getDefaultCacheConfiguration()} if {@literal null}.
     * @return redis缓存
     */
    @Override
    protected @NotNull RedisCache createRedisCache(@NotNull String name, RedisCacheConfiguration cacheConfiguration) {
        String[] params = StringUtils.delimitedListToStringArray(name, "#");
        name = params[0];

        // 解析ttl
        if (params.length > 1) {
            long time = getTime(params);
            return super.createRedisCache(name, cacheConfiguration.entryTtl(Duration.ofSeconds(time)));
        }

        return super.createRedisCache(name, cacheConfiguration);
    }

    /**
     * 解析时间秒数
     *
     * @param params 参数
     * @return 时间秒数
     */
    private static long getTime(String[] params) {
        String ttl = params[1];
        // 配置缓存到期时间
        long cycle = Long.parseLong(ttl);
        // 单位
        String unit = ttl.substring(ttl.length() - 1);

        long time = 0;
        switch (unit) {
            // 秒
            case "s" -> time = cycle;
            // 分
            case "m" -> time = 60 * cycle;
            // 时
            case "h" -> time = 60 * 60 * cycle;
            // 天
            case "d" -> time = 24 * 60 * 60 * cycle;
        }
        return time;
    }

}

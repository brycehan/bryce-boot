package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.system.service.PasswordRetryService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 密码重试服务实现
 *
 * @since 2022/9/29
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class PasswordRetryServiceImpl implements PasswordRetryService {

    private final RedisTemplate<String, Integer> redisTemplate;

    @Value(value = "${bryce.user.password.lock-duration-minutes}")
    private Integer lockDurationMinutes;

    /**
     * 获取登录账户密码错误次数缓存键
     *
     * @param username 账号
     * @return 缓存键
     */
    private String getPasswordErrorCountCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_COUNT_KEY.concat(username);
    }

    @Override
    public void retryCount(String username) {
        // 从缓存获取用户账号密码错误次数
        Integer retryCount = this.redisTemplate.opsForValue().get(getPasswordErrorCountCacheKey(username));

        // 累加次数
        if (retryCount == null) {
            retryCount = 0;
        }

        this.redisTemplate.opsForValue().set(
                getPasswordErrorCountCacheKey(username),
                ++retryCount,
                this.lockDurationMinutes,
                TimeUnit.MINUTES
        );
    }

    @Override
    public void deleteCount(String username) {
        this.redisTemplate.delete(getPasswordErrorCountCacheKey(username));
    }

}

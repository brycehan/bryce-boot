package com.brycehan.boot.framework.security;

import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.response.UserResponseStatus;
import com.brycehan.boot.common.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

/**
 * @since 2023/10/9
 * @author Bryce Han
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserDetailsCheck implements UserDetailsChecker {

    private final RedisTemplate<String, Integer> redisTemplate;

    @Value(value = "${bryce.user.password.max-retry-count}")
    private Integer maxRetryCount;

    @Value(value = "${bryce.user.password.lock-duration-minutes}")
    private Integer lockDurationMinutes;

    @Override
    public void check(UserDetails toCheck) {
        log.debug(JsonUtils.writeValueAsString(toCheck));

        // 超过密码错误最大次数锁定账户
        Integer retryCount = this.redisTemplate.opsForValue()
                .get(getPasswordErrorCountCacheKey(toCheck.getUsername()));

        if (retryCount == null) {
            retryCount = 0;
        }
        if (retryCount >= maxRetryCount) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_RETRY_LIMIT_EXCEEDED, retryCount.toString(), this.lockDurationMinutes.toString());
        }
    }

    /**
     * 获取登录账户密码错误次数缓存键
     *
     * @param username 账号
     * @return 缓存键
     */
    private String getPasswordErrorCountCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_COUNT_KEY + username;
    }

}

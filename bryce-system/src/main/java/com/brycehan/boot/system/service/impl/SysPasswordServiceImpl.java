package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.exception.user.UserPasswordRetryLimitExceedException;
import com.brycehan.boot.common.util.PasswordUtils;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysPasswordService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 系统密码服务类
 *
 * @author Bryce Han
 * @since 2022/9/29
 */
@Service
public class SysPasswordServiceImpl implements SysPasswordService {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Value(value = "${bryce.user.password.max-retry-count}")
    private Integer maxRetryCount;

    @Value(value = "${bryce.user.password.lock-duration-minutes}")
    private Integer lockDurationMinutes;

    /**
     * 获取登录账户密码错误次数缓存键
     *
     * @param username 账号
     * @return 缓存键
     */
    private String getPasswordErrorCountCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_COUNT_KEY + username;
    }

    @Override
    public void validate(SysUser sysUser) {
        // 1、获取用户录入账户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 2、超过密码错误最大次数锁定账户
        Integer retryCount = this.redisTemplate.opsForValue().get(getPasswordErrorCountCacheKey(username));
        if (Objects.isNull(retryCount)) {
            retryCount = 0;
        }
        if (retryCount >= maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(retryCount, this.lockDurationMinutes);
        }
        // 3、密码匹配时清除密码错误次数缓存，不匹配则记录错误次数
        if (PasswordUtils.matches(password, sysUser.getPassword())) {
            this.redisTemplate.delete(getPasswordErrorCountCacheKey(username));
        } else {
            retryCount++;
            this.redisTemplate.opsForValue().set(
                    getPasswordErrorCountCacheKey(username),
                    retryCount,
                    this.lockDurationMinutes,
                    TimeUnit.MINUTES
            );

            throw BusinessException
                    .responseStatus(UserResponseStatusEnum.USER_USERNAME_OR_PASSWORD_ERROR);
        }
    }

    public static void main(String[] args) {

    }

}

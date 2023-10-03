package com.brycehan.boot.common.exception.user;

import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.exception.BusinessException;

/**
 * 用户密码错误重试最大次数异常类
 *
 * @since 2022/9/22
 * @author Bryce Han
 */
public class UserPasswordRetryLimitExceedException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount, int lockTime) {
        super(UserResponseStatus.USER_PASSWORD_RETRY_LIMIT_EXCEEDED, Integer.toString(retryLimitCount), Integer.toString(lockTime));
    }
}

package com.brycehan.boot.common.exception.user;

import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.exception.BusinessException;

/**
 * 用户验证码失效异常
 *
 * @author Bryce Han
 * @since 2022/9/22
 */
public class UserCaptchaExpireException extends BusinessException {

    public UserCaptchaExpireException() {
        super(UserResponseStatus.USER_CAPTCHA_EXPIRE);
    }

}

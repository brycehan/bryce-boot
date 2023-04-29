package com.brycehan.boot.common.exception.user;

import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.exception.BusinessException;

/**
 * 用户验证码异常
 *
 * @author Bryce Han
 * @since 2022/9/22
 */
public class UserCaptchaException extends BusinessException {

    public static final long serialVersionUID = 1L;

    public UserCaptchaException() {
        super(CommonConstants.SYSTEM_MODULE, UserResponseStatusEnum.USER_CAPTCHA_EXPIRE);
    }

}

package com.brycehan.boot.common.exception.user;

import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.exception.BusinessException;

/**
 * 用户密码不正确
 *
 * @author Bryce Han
 * @since 2022/9/22
 */
public class UserPasswordNotMatchException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super(UserResponseStatus.USER_PASSWORD_NOT_MATCH);
    }
}

package com.brycehan.boot.system.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录信息
 *
 * @since 2023/9/25
 * @author Bryce Han
 */
@Getter
@AllArgsConstructor
public enum LoginInfoType {
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(0),
    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(1),
    /**
     * 验证码错误
     */
    CAPTCHA_FAIL(2),
    /**
     * 账号密码错误
     */
    ACCOUNT_FAIL(3);

    private final int value;
}

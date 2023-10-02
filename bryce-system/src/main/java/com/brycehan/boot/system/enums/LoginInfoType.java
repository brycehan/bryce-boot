package com.brycehan.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录信息
 *
 * @author Bryce Han
 * @since 2023/9/25
 */
@Getter
@AllArgsConstructor
public enum LoginInfoType {
    /** 登录成功 */
    LOGIN_SUCCESS(0),
    /** 退出成功 */
    LOGOUT_SUCCESS(1),
    /** 验证码错误 */
    CAPTCHA_FAIL(2),
    /** 账号密码错误 */
    ACCOUNT_FAIL(3);

    private final int value;
}

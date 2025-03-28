package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 权限响应状态枚举
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthResponseStatus implements ResponseStatus {

    // AUTH
    AUTH_LOGIN_BAD_CREDENTIALS(1_000_000, "登录失败，账号密码不正确"),
    AUTH_LOGIN_USER_DISABLED(1_000_001, "登录失败，账号被禁用"),
    AUTH_LOGIN_CAPTCHA_CODE_ERROR(1_000_004, "验证码不正确，原因：{}"),
    AUTH_THIRD_LOGIN_NOT_BIND(1_000_005, "未绑定账号，需要进行绑定"),
    AUTH_MOBILE_NOT_EXISTS(1_000_007, "手机号不存在"),
    AUTH_REGISTER_CAPTCHA_CODE_ERROR(1_000_008, "验证码不正确，原因：{}"),
    AUTH_TOKEN_INVALID(1_000_009, "用户令牌无效"),

    ;

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}

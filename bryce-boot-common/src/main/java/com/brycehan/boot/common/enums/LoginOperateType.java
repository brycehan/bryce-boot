package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 登录操作类型
 *
 * @since 2023/9/25
 * @author Bryce Han
 */
@Getter
public enum LoginOperateType {
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(0, "登录成功"),
    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(1, "退出成功"),
    /**
     * 验证码错误
     */
    CAPTCHA_FAIL(2, "验证码错误"),
    /**
     * 账号密码错误
     */
    ACCOUNT_FAIL(3, "账号不存在/密码错误");

    @JsonValue
    @EnumValue
    private final int value;
    private final String desc;

    LoginOperateType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}

package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 验证码类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
public enum CaptchaType {
    /**
     * 登录
     */
    LOGIN("login"),
    /**
     * 注册
     */
    REGISTER("register");

    @JsonValue
    private final String value;

    CaptchaType(String value) {
        this.value = value;
    }
}

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

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

    /**
     * 类型值
     */
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    CaptchaType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}

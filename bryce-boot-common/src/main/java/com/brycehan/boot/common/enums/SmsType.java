package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 短信类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@Getter
public enum SmsType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

    @JsonValue
    private final String value;
    private final String desc;

    SmsType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}

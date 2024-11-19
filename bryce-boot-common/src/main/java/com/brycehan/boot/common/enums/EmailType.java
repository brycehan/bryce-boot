package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 邮件类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@Getter
public enum EmailType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册账号");

    /**
     * 类型值
     */
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    EmailType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}

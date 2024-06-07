package com.brycehan.boot.common.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 短信类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SmsType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

    private final String value;
    private final String desc;

    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }
}

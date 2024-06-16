package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * 邮件类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@RequiredArgsConstructor
public enum EmailType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册账号");

    private final String value;
    private final String desc;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EmailType getByValue(String value) {
        for (EmailType emailType : values()) {
            if (emailType.value.equals(value)) {
                return emailType;
            }
        }
        return null;
    }

}

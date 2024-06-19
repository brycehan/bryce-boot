package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * 短信类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@RequiredArgsConstructor
public enum SmsType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

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
    public static SmsType getByValue(String value) {
        for (SmsType smsType : values()) {
            if (smsType.value.equals(value)) {
                return smsType;
            }
        }
        return null;
    }

}

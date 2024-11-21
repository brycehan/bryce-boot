package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短信类型
 *
 * @author Bryce Han
 * @since 2024/5/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum SmsType implements EnumType {

    LOGIN("login", "登录"),
    REGISTER("register", "注册");

    /**
     * 值
     */
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static SmsType getByDesc(String desc) {
        for (SmsType smsType : SmsType.values()) {
            if (smsType.getDesc().equals(desc)) {
                return smsType;
            }
        }
        return null;
    }

}

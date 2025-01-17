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
@SuppressWarnings("unused")
@RequiredArgsConstructor
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
    @DescValue
    private final String desc;

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static EmailType of(String value) {
        for (EmailType emailType : EmailType.values()) {
            if (emailType.getValue().equals(value)) {
                return emailType;
            }
        }
        return null;
    }

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static EmailType getByDesc(String desc) {
        for (EmailType emailType : EmailType.values()) {
            if (emailType.getDesc().equals(desc)) {
                return emailType;
            }
        }
        return null;
    }

}

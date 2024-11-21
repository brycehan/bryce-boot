package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 验证码类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum CaptchaType implements EnumType {

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

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static CaptchaType getByValue(String value) {
        for (CaptchaType captchaType : values()) {
            if (captchaType.getValue().equals(value)) {
                return captchaType;
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
    public static CaptchaType getByDesc(String desc) {
        for (CaptchaType captchaType : values()) {
            if (captchaType.getDesc().equals(desc)) {
                return captchaType;
            }
        }
        return null;
    }

}

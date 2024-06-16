package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * 验证码类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@RequiredArgsConstructor
public enum CaptchaType {
    /**
     * 登录
     */
    LOGIN("login"),
    /**
     * 注册
     */
    REGISTER("register");

    private final String value;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
    public String value() {
        return value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CaptchaType getByValue(String value) {
        for (CaptchaType captchaType : values()) {
            if (captchaType.value.equals(value)) {
                return captchaType;
            }
        }
        return null;
    }

}

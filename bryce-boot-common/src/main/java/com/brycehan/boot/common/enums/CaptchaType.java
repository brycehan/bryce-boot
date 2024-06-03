package com.brycehan.boot.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 验证码类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
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

    public static CaptchaType getByValue(String value) {
        for (CaptchaType captchaType : CaptchaType.values()) {
            if (captchaType.getValue().equals(value)) {
                return captchaType;
            }
        }
        return null;
    }
}

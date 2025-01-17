package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 登录状态
 *
 * @author Bryce Han
 * @since 2023/9/25
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum LoginStatus {

    LOGIN_SUCCESS(0, "登录成功"),
    LOGOUT_SUCCESS(1, "退出成功"),
    CAPTCHA_FAIL(2, "验证码错误"),
    ACCOUNT_FAIL(3, "账号不存在/密码错误");

    /**
     * 类型值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

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
    public static LoginStatus of(Integer value) {
        for (LoginStatus loginStatus : LoginStatus.values()) {
            if (loginStatus.getValue().equals(value)) {
                return loginStatus;
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
    public static LoginStatus getByDesc(String desc) {
        for (LoginStatus loginStatus : LoginStatus.values()) {
            if (loginStatus.getDesc().equals(desc)) {
                return loginStatus;
            }
        }
        return null;
    }

}

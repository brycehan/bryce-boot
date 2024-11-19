package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 登录操作类型
 *
 * @since 2023/9/25
 * @author Bryce Han
 */
@Getter
public enum LoginOperateType {

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
    private final String desc;

    LoginOperateType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}

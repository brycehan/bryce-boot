package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 用户响应状态枚举
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum UserResponseStatus implements ResponseStatus {

    USER_REGISTER_NOT_ENABLED(1400, "当前系统没有开启注册功能"),

    USER_REGISTER_EXISTS(1401, "注册{}失败，账号已存在"),

    USER_REGISTER_ERROR(1402, "注册失败，请联系系统管理人员"),

    USER_REGISTER_SUCCESS(1403, "注册成功"),

    USER_ACCOUNT_NOT_EXISTS(1404, "该账号{}还未注册，请先注册"),

    USER_ACCOUNT_LOCKED(1405, "用户帐号已被锁定"),

    USER_ACCOUNT_DISABLED(1406, "用户账号已停用"),

    USER_USERNAME_OR_PASSWORD_ERROR(1408, "账号与密码不匹配"),

    USER_USERNAME_NOT_VALID(1409, "2到20个汉字、字母、数字或下划线组成，且必须以非数字开头"),

    USER_EMAIL_NOT_VALID(1410, "邮箱格式错误"),

    USER_PHONE_NUMBER_NOT_VALID(1411, "手机号格式错误"),

    USER_PASSWORD_NOT_VALID(1412, "6-30个字符"),

    USER_PASSWORD_RETRY_LIMIT_EXCEEDED(1413, "密码输入错误{}次，帐户锁定{}"),

    USER_PASSWORD_NOT_MATCH(1414, "原密码错误"),

    USER_PASSWORD_SAME_AS_OLD_ERROR(1415, "新密码不能与旧密码相同"),

    USER_PASSWORD_CHANGE_ERROR(1416, "修改密码异常，请联系管理员"),

    USER_CAPTCHA_ERROR(1417, "验证码错误"),

    USER_CAPTCHA_EXPIRE(1418, "验证码已失效"),

    USER_TOKEN_INVALID(1419, "用户令牌无效"),

    USER_LOGOUT_SUCCESS(1420, "退出成功"),

    USER_LOGIN_SUCCESS(1421, "登录成功"),

    USER_FORCE_LOGOUT(1422, "管理员强制退出，请重新登录"),

    USER_PROFILE_PHONE_EXISTS(1423, "修改用户{}失败，手机号码已存在"),

    USER_PROFILE_EMAIL_EXISTS(1424, "修改用户{}失败，邮箱已存在"),

    USER_PROFILE_ALTER_INFO_ERROR(1425, "修改个人信息异常，请联系管理员"),

    USER_PROFILE_ALTER_AVATAR_ERROR(1426, "修改头像异常，请联系管理员"),

    USER_BALANCE_INSUFFICIENT(1427, "账户余额不足，还差{}元");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}

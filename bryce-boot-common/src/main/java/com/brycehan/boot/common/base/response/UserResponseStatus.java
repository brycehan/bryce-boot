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

    USER_REGISTER_NOT_ENABLED(600, "当前系统没有开启注册功能"),

    USER_REGISTER_EXISTS(601, "注册{}失败，账号已存在"),

    USER_REGISTER_ERROR(602, "注册失败，请联系系统管理人员"),

    USER_REGISTER_SUCCESS(603, "注册成功"),

    USER_ACCOUNT_NOT_EXISTS(604, "该账号{}还未注册，请先注册"),

    USER_ACCOUNT_LOCKED(605, "用户帐号已被锁定"),

    USER_ACCOUNT_DISABLED(606, "用户账号已停用"),

    USER_USERNAME_OR_PASSWORD_ERROR(608, "账号与密码不匹配"),

    USER_USERNAME_NOT_VALID(609, "2到20个汉字、字母、数字或下划线组成，且必须以非数字开头"),

    USER_EMAIL_NOT_VALID(610, "邮箱格式错误"),

    USER_PHONE_NUMBER_NOT_VALID(611, "手机号格式错误"),

    USER_PASSWORD_NOT_VALID(612, "6-30个字符"),

    USER_PASSWORD_RETRY_LIMIT_EXCEEDED(613, "密码输入错误{}次，帐户锁定{}分钟"),

    USER_PASSWORD_NOT_MATCH(614, "原密码错误"),

    USER_PASSWORD_SAME_AS_OLD_ERROR(615, "新密码不能与旧密码相同"),

    USER_PASSWORD_CHANGE_ERROR(616, "修改密码异常，请联系管理员"),

    USER_CAPTCHA_ERROR(617, "验证码错误"),

    USER_CAPTCHA_EXPIRE(618, "验证码已失效"),

    USER_TOKEN_INVALID(619, "用户令牌无效"),

    USER_LOGOUT_SUCCESS(620, "退出成功"),

    USER_LOGIN_SUCCESS(621, "登录成功"),

    USER_FORCE_LOGOUT(622, "管理员强制退出，请重新登录"),

    USER_PROFILE_PHONE_EXISTS(623, "修改用户{}失败，手机号码已存在"),

    USER_PROFILE_EMAIL_EXISTS(624, "修改用户{}失败，邮箱已存在"),

    USER_PROFILE_ALTER_INFO_ERROR(625, "修改个人信息异常，请联系管理员"),

    USER_PROFILE_ALTER_AVATAR_ERROR(626, "修改头像异常，请联系管理员"),

    USER_BALANCE_INSUFFICIENT(627, "账户余额不足，还差{}元");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}

package com.brycehan.boot.common.base.http;

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

    USER_REGISTER_NOT_ENABLED(600, "user.register.not.enabled"),

    USER_REGISTER_EXISTS(601, "user.register.exists"),

    USER_REGISTER_ERROR(602, "user.register.error"),

    USER_REGISTER_SUCCESS(603, "user.register.success"),

    USER_ACCOUNT_NOT_EXISTS(604, "user.account.not.exists"),

    USER_ACCOUNT_LOCKED(605, "user.account.locked"),

    USER_ACCOUNT_DISABLED(606, "user.account.disabled"),

    USER_ACCOUNT_DELETED(607, "user.account.deleted"),

    USER_USERNAME_OR_PASSWORD_ERROR(608, "user.username.or.password.error"),

    USER_USERNAME_NOT_VALID(609, "user.username.not.valid"),

    USER_EMAIL_NOT_VALID(610, "user.email.not.valid"),

    USER_PHONE_NUMBER_NOT_VALID(611, "user.phone.number.not.valid"),

    USER_PASSWORD_NOT_VALID(612, "user.password.not.valid"),

    USER_PASSWORD_RETRY_LIMIT_EXCEEDED(613, "user.password.retry.limit.exceeded"),

    USER_PASSWORD_NOT_MATCH(614, "user.password.not.match"),

    USER_PASSWORD_SAME_AS_OLD_ERROR(615, "user.password.same.as.old.error"),

    USER_PASSWORD_CHANGE_ERROR(616, "user.password.change.error"),

    USER_CAPTCHA_ERROR(617, "user.captcha.error"),

    USER_CAPTCHA_EXPIRE(618, "user.captcha.expire"),

    USER_TOKEN_INVALID(619, "user.token.invalid"),

    USER_LOGOUT_SUCCESS(620, "user.logout.success"),

    USER_LOGIN_SUCCESS(621, "user.login.success"),

    USER_FORCE_LOGOUT(622, "user.force.logout"),

    USER_PROFILE_PHONE_INVALID(623, "user.profile.phone.invalid"),

    USER_PROFILE_EMAIL_INVALID(624, "user.profile.email.invalid"),

    USER_PROFILE_ALTER_ERROR(625, "user.profile.alter.error"),

    USER_BALANCE_INSUFFICIENT(626, "user.balance.insufficient");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}

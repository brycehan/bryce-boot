package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 用户响应状态枚举
 *
 * @author Bryce Han
 * @since 2022/5/30
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum UserResponseStatusEnum implements ResponseStatus {

    USER_REGISTER_NOT_ENABLED(600, MessageUtils.message("user.register.not.enabled")),

    USER_REGISTER_EXISTS(601, MessageUtils.message("user.register.exists")),

    USER_REGISTER_ERROR(602, MessageUtils.message("user.register.error")),

    USER_REGISTER_SUCCESS(603, MessageUtils.message("user.register.success")),

    USER_ACCOUNT_NOT_EXISTS(604, MessageUtils.message("user.account.not.exists")),

    USER_ACCOUNT_LOCKED(605, MessageUtils.message("user.account.locked")),

    USER_ACCOUNT_DISABLED(606, MessageUtils.message("user.account.disabled")),

    USER_ACCOUNT_DELETED(607, MessageUtils.message("user.account.deleted")),

    USER_USERNAME_OR_PASSWORD_ERROR(608, MessageUtils.message("user.username.or.password.error")),

    USER_USERNAME_NOT_VALID(609, MessageUtils.message("user.username.not.valid")),

    USER_EMAIL_NOT_VALID(610, MessageUtils.message("user.email.not.valid")),

    USER_PHONE_NUMBER_NOT_VALID(611, MessageUtils.message("user.phone.number.not.valid")),

    USER_PASSWORD_NOT_VALID(612, MessageUtils.message("user.password.not.valid")),

    USER_PASSWORD_RETRY_LIMIT_EXCEEDED(613, MessageUtils.message("user.password.retry.limit.exceeded")),

    USER_PASSWORD_NOT_MATCH(614, MessageUtils.message("user.password.not.match")),

    USER_PASSWORD_SAME_AS_OLD_ERROR(615, MessageUtils.message("user.password.same.as.old.error")),

    USER_PASSWORD_CHANGE_ERROR(616, MessageUtils.message("user.password.change.error")),

    USER_CAPTCHA_ERROR(617, MessageUtils.message("user.captcha.error")),

    USER_CAPTCHA_EXPIRE(618, MessageUtils.message("user.captcha.expire")),

    USER_TOKEN_INVALID(619, MessageUtils.message("user.token.invalid")),

    USER_LOGOUT_SUCCESS(620, MessageUtils.message("user.logout.success")),

    USER_LOGIN_SUCCESS(621, MessageUtils.message("user.login.success")),

    USER_FORCE_LOGOUT(622, MessageUtils.message("user.force.logout")),

    USER_PROFILE_PHONE_INVALID(623, MessageUtils.message("user.profile.phone.invalid")),

    USER_PROFILE_EMAIL_INVALID(624, MessageUtils.message("user.profile.email.invalid")),

    USER_PROFILE_ALTER_ERROR(625, MessageUtils.message("user.profile.alter.error")),

    USER_BALANCE_INSUFFICIENT(626, MessageUtils.message("user.balance.insufficient"));

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

}

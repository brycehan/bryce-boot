package com.brycehan.boot.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则工具类
 *
 * @author Bryce Han
 * @since 2024/8/20
 */
@SuppressWarnings("unused")
public class RegexUtils {

    /**
     * 校验手机号
     *
     * @param phone 手机号
     * @return true 是手机号，false 不是手机号
     */
    public static boolean isPhoneValid(String phone) {
        return matches(phone, RegexPatterns.PHONE_REGEX);
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return true 是邮箱，false 不是邮箱
     */
    public static boolean isEmailValid(String email) {
        return matches(email, RegexPatterns.EMAIL_REGEX);
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @return true 是密码，false 不是密码
     */
    public static boolean isPasswordValid(String password) {
        return matches(password, RegexPatterns.PASSWORD_REGEX);
    }

    /**
     * 校验验证码
     *
     * @param verifyCode 验证码
     * @return true 是验证码，false 不是验证码
     */
    public static boolean isVerifyCodeValid(String verifyCode) {
        return matches(verifyCode, RegexPatterns.VERIFY_CODE_REGEX);
    }

    /**
     * 正则匹配
     *
     * @param value 待匹配的值
     * @param regex 正则字符串
     * @return 是否匹配
     */
    private static boolean matches(String value, String regex) {
        if (regex == null || StringUtils.isEmpty(value)){
            return false;
        }

        return value.matches(regex);
    }
}

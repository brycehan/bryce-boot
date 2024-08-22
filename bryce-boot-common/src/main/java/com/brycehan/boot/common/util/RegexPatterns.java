package com.brycehan.boot.common.util;

/**
 * 正则表达式
 *
 * @author Bryce Han
 * @since 2024/8/20
 */
public class RegexPatterns {

    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 密码正则 8-32位，至少包含一个字母，一个数字，一个特殊字符
     */
    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[\\w!@#$%^&*]{8,32}$";

    /**
     * 验证码正则
     */
    public static final String VERIFY_CODE_REGEX = "^\\d{6}$";

}

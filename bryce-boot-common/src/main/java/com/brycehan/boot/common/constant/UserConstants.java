package com.brycehan.boot.common.constant;

/**
 * 用户常量
 *
 * @since 2022/9/22
 * @author Bryce Han
 */
public class UserConstants {

    /**
     * null 用户ID
     */
    public static final Long NULL_USER_ID = -1L;

    /**
     * 校验返回结果码
     */
    public final static String UNIQUE = "1";
    public final static String NOT_UNIQUE = "0";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 30;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 20;

}

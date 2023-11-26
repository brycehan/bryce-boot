package com.brycehan.boot.common.constant;

/**
 * Jwt常量
 *
 * @since 2022/5/12
 * @author Bryce Han
 */
public class JwtConstants {

    /**
     * jwt前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户令牌key
     */
    public static final String LOGIN_USER_KEY = "login_user";

    /**
     * 刷新最小分钟数
     */
    public static final long REFRESH_LIMIT_MIN_MINUTE = 20L;

}

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
     * 登录用户key
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户数据
     */
    public static final String USER_DATA = "user_data";

    /**
     * 登录用户缓存Key
     */
    public static final String LOGIN_USER_KEY = "login_user";

    /**
     * 登录用户openid
     */
    public static final String LOGIN_OPEN_ID = "openid";

    /**
     * 刷新令牌，缓存会话最小分钟数
     */
    public static final long REFRESH_CACHE_MIN_MINUTE = 60L;

    /**
     * 刷新令牌，App会话最小天数
     */
    public static final long REFRESH_APP_MIN_DAY = 1L;

}

package com.brycehan.boot.common.constant;

/**
 * Jwt常量
 *
 * @author Bryce Han
 * @since 2022/5/12
 */
public class JwtConstants {

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 认证请求头
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 登录用户令牌键
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户账号
     */
    public static final String JWT_USERNAME = "username";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "createdTime";

    /**
     * 刷新最小分钟数
     */
    public static final long REFRESH_LIMIT_MIN_MINUTE = 20;

}

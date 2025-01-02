package com.brycehan.boot.framework.security;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.common.enums.SourceClientType;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.config.AuthProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Jwt令牌工具类
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthProperties authProperties;

    private final RedisTemplate<String, LoginUser> redisTemplate;

    /**
     * 生成token
     *
     * @param loginUser 登录用户
     * @return jwt 令牌
     */
    public String generateToken(LoginUser loginUser) {
        // 创建jwt令牌
        Map<String, Object> claims = new HashMap<>();

        AuthProperties.Jwt jwt = authProperties.getJwt();
        LocalDateTime now = LocalDateTime.now();

        loginUser.setRememberMe(Boolean.TRUE.equals(LoginUserContext.rememberMeHolder.get()));
        loginUser.setLoginTime(now);

        // 判断是否记住我
        if (loginUser.getRememberMe()) { // 记住我
            // 设置过期时间
            loginUser.setExpireTime(now.plus(jwt.getAppTokenValidity()));
            claims.put(JwtConstants.USER_DATA, JsonUtils.writeValueAsString(loginUser));
        } else { // 没有记住我
            switch (Objects.requireNonNull(loginUser.getSourceClientType())) {
                case PC, H5, UNKNOWN -> {
                    loginUser.setUserKey(TokenUtils.uuid());
                    loginUser.setExpireTime(now.plus(jwt.getDefaultTokenValidity()));
                    claims.put(JwtConstants.USER_KEY, loginUser.getUserKey());
                }
                case APP, MINI_APP -> {
                    loginUser.setExpireTime(now.plus(jwt.getAppTokenValidity()));
                    claims.put(JwtConstants.USER_DATA, JsonUtils.writeValueAsString(loginUser));
                }
            }
        }


        return generateToken(claims, loginUser.getExpireTime());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @param expiredTime 过期时间
     * @return 令牌
     */
    public String generateToken(Map<String, Object> claims, LocalDateTime expiredTime) {
        AuthProperties.Jwt jwt = authProperties.getJwt();
        if (jwt == null || StrUtil.isBlank(jwt.getSecret())) {
            return null;
        }

        // 指定加密方式
        Algorithm algorithm = Algorithm.HMAC256(jwt.getSecret());
        // 过期时间
        Instant expiredTimeInstant = expiredTime.atZone(ZoneId.systemDefault()).toInstant();
        return JWT.create()
                .withPayload(claims)
                .withExpiresAt(expiredTimeInstant)
                // 签发 JWT
                .sign(algorithm);
    }

    public void cache(LoginUser loginUser) {
        // 记住我
        if (Boolean.TRUE.equals(loginUser.getRememberMe())) {
            return;
        }

        // 来源客户端
        SourceClientType sourceClientType = loginUser.getSourceClientType();
        LocalDateTime now = LocalDateTime.now();
        // 过期时间间隔
        Duration expireTimeDuration = Duration.between(now, loginUser.getExpireTime());

        switch (sourceClientType) {
            case PC, H5, UNKNOWN -> {
                String cacheUserKey = JwtConstants.LOGIN_USER_KEY.concat(":").concat(loginUser.getUserKey());

                // 根据tokenKey将loginUser缓存
                this.redisTemplate.opsForValue()
                        .set(cacheUserKey, loginUser, expireTimeDuration.toSeconds(), TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 令牌自动续期
     *
     * @param loginUser 登录用户
     */
    public void autoRefreshToken(LoginUser loginUser) {
        if (needRefreshToken(loginUser)) {
            doRefreshToken(loginUser);
        }
    }

    /**
     * 令牌续期
     *
     * @param loginUser 登录用户
     */
    public void doRefreshToken(LoginUser loginUser) {
        if (loginUser == null) {
            return;
        }
        // 生成 jwt
        String token = generateToken(loginUser);
        // 缓存 loginUser
        cache(loginUser);
        // 刷新令牌
        refreshToken(token);
    }

    /**
     * 判断是否需要刷新令牌
     *
     * @param loginUser 登录用户
     * @return 是否需要刷新令牌
     */
    public boolean needRefreshToken(LoginUser loginUser) {
        LocalDateTime loginTime = loginUser.getLoginTime();
        LocalDateTime expireTime = loginUser.getExpireTime();
        LocalDateTime now = LocalDateTime.now();
        // 登录至过期的一半时间间隔
        Duration mediumDuration = Duration.between(loginTime, expireTime).dividedBy(2);
        // 令牌需要续期
        return now.isBefore(expireTime) && now.isAfter(loginTime.plus(mediumDuration));
    }

    /**
     * 刷新令牌有效期
     *
     * @param token 新的令牌
     */
    public void refreshToken(String token) {
        HttpServletResponse response = ServletUtils.getResponse();
        if(response != null) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
            // 将 jwt token 添加到响应头
            response.setHeader(HttpHeaders.AUTHORIZATION, JwtConstants.TOKEN_PREFIX.concat(token));
        }
    }

    /**
     * 获取登录用户信息
     *
     * @param userKey 用户key
     * @return 登录用户
     */
    public LoginUser loadLoginUser(String userKey) {
        try {
            String cacheUserKey = JwtConstants.LOGIN_USER_KEY.concat(":").concat(userKey);
            return this.redisTemplate.opsForValue().get(cacheUserKey);
        } catch (Exception e) {
            log.warn("loadLoginUser, 异常：{}", e.getMessage());
        }

        return null;
    }

    /**
     * 删除登录用户
     *
     * @param userKey 会话存储key
     */
    public void deleteLoginUser(String userKey) {
        if (StrUtil.isNotBlank(userKey)) {
            String loginUserKey = CacheConstants.LOGIN_USER_KEY.concat(userKey);
            this.redisTemplate.delete(loginUserKey);
        }
    }

    /**
     * 获取用户key
     *
     * @param claimMap 数据声明
     * @return 用户key
     */
    public static String getUserKey(Map<String, Claim> claimMap) {
        if (claimMap.get(JwtConstants.USER_KEY) == null) {
            return null;
        }

        return claimMap.get(JwtConstants.USER_KEY).asString();
    }

    /**
     * 获取用户数据
     *
     * @param claimMap 数据声明
     * @return 用户数据
     */
    public static String getUserData(Map<String, Claim> claimMap) {
        if (claimMap.get(JwtConstants.USER_DATA) == null) {
            return null;
        }

        return claimMap.get(JwtConstants.USER_DATA).asString();
    }

    /**
     * 校验token
     *
     * @param authToken 令牌
     * @return 校验令牌是否有效（true：有效，false：无效）
     */
    public DecodedJWT validateToken(String authToken) {
        AuthProperties.Jwt jwt = authProperties.getJwt();
        if (jwt == null || StrUtil.isBlank(jwt.getSecret())) {
            return null;
        }

        Algorithm algorithm = Algorithm.HMAC256(jwt.getSecret());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(authToken);
    }
}

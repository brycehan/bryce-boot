package com.brycehan.boot.framework.security;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blueconic.browscap.Capabilities;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.LocationUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.common.util.UserAgentUtils;
import com.brycehan.boot.framework.security.context.LoginUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Jwt令牌工具类
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * jwt密钥
     */
    @Value("${bryce.jwt.secret}")
    private String jwtSecret = "UZCiSM60eRJMOFA9mbiy";

    /**
     * 2小时过期
     */
    @Value("${bryce.jwt.token-validity-in-seconds}")
    // todo 单位调整为分钟
    private long tokenValidityInSeconds;

    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

    /**
     * 获取登录用户信息
     *
     * @param accessToken 访问令牌
     * @return 登录用户
     */
    public LoginUser getLoginUser(String accessToken) {
        try {
            // 解析对应的权限以及用户信息
            Map<String, Claim> claimMap = parseToken(accessToken);
            String uuid = claimMap.get(JwtConstants.LOGIN_USER_KEY).asString();
            String loginUserKey = CacheConstants.LOGIN_USER_KEY.concat(uuid);
            return this.redisTemplate.opsForValue().get(loginUserKey);
        } catch (Exception e) {
            log.warn("JwtTokenProvider.getLoginUser, 异常：{}", e.getMessage());
        }

        return null;
    }

    /**
     * 设置登录用户
     *
     * @param loginUser 登录用户
     */
    public void setLoginUser(LoginUser loginUser) {
        if (Objects.nonNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除登录用户
     *
     * @param accessToken 令牌
     */
    public void deleteLoginUser(String accessToken) {
        if (StrUtil.isNotBlank(accessToken)) {
            String loginUserKey = CacheConstants.LOGIN_USER_KEY.concat(accessToken);
            this.redisTemplate.delete(loginUserKey);
        }
    }

    /**
     * 生成token
     *
     * @param loginUser 登录用户
     * @return 令牌
     */
    public String generateToken(LoginUser loginUser) {

        // 1、生成token
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        // 2、设置用户代理
        setUserAgent(loginUser);

        // 3、刷新令牌
        refreshToken(loginUser);

        // 4、创建jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.LOGIN_USER_KEY, token);

        return generateToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        // 指定加密方式
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        return JWT.create()
                .withPayload(claims)
                // 签发 JWT
                .sign(algorithm);
    }

    /**
     * 设置用户代理
     *
     * @param loginUser 登录用户
     */
    private void setUserAgent(LoginUser loginUser) {
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        Capabilities capabilities = UserAgentUtils.parser.parse(userAgent);

        // 获取客户端浏览器
        String browser = capabilities.getBrowser();
        // 获取客户端操作系统
        String platform = capabilities.getPlatform();
        // 获取客户端IP和对应登录位置
        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        String loginLocation = LocationUtils.getLocationByIP(ip);

        loginUser.setIpAddress(ip);
        loginUser.setLoginLocation(loginLocation);
        loginUser.setBrowser(browser);
        loginUser.setOs(platform);
    }

    /**
     * 令牌自动续期，相差不足20分钟，自动刷新延长登录有效期
     *
     * @param loginUser 登录用户
     */
    public void autoRefreshToken(LoginUser loginUser) {
        LocalDateTime expireTime = loginUser.getExpireTime();
        LocalDateTime now = LocalDateTime.now();

        if (expireTime.isAfter(now) && expireTime.isBefore(now.plusMinutes(JwtConstants.REFRESH_LIMIT_MIN_MINUTE))) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录用户
     */
    private void refreshToken(LoginUser loginUser) {
        LocalDateTime now = LocalDateTime.now();
        loginUser.setLoginTime(now);
        loginUser.setExpireTime(now.plusSeconds(this.tokenValidityInSeconds));
        // 根据uuid将loginUser缓存
        String loginUserKey = CacheConstants.LOGIN_USER_KEY.concat(loginUser.getToken());
        this.redisTemplate.opsForValue()
                .set(loginUserKey, loginUser, tokenValidityInSeconds, TimeUnit.SECONDS);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Map<String, Claim> parseToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaims();
    }

    /**
     * 校验token
     *
     * @param authToken 令牌
     * @return 校验令牌是否有效（true：有效，false：无效）
     */
    @Deprecated
    public boolean validateToken(String authToken) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}

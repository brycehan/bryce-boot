package com.brycehan.boot.framework.security.config;

import com.brycehan.boot.common.constant.JwtConstants;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

/**
 * 认证属性
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "bryce.auth")
public class AuthProperties {

    /**
     * Jwt 配置属性
     */
    private Jwt jwt = new Jwt();

    /**
     * 忽略的资源地址
     */
    private IgnoreUrls ignoreUrls;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        if (JwtConstants.secret.equals(jwt.getSecret())) {
            log.warn("认证配置 bryce.auth.secret 使用默认配置，请在生产环境配置，否则可能存在安全风险");
        }
    }

    @Data
    public static class Jwt implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 密钥
         */
        private String secret = JwtConstants.secret;

        /**
         * 授权key
         */
        private String authoritiesKey = "auth";

        /**
         * web token有效期
         */
        private Duration webTokenValidity = Duration.ofHours(2L);

        /**
         * App token有效期
         */
        private Duration appTokenValidity =  Duration.ofDays(30L);

        /**
         * 是否启用缓存
         */
        private boolean  cacheEnable = true;
    }

    @Getter
    @Setter
    public static class IgnoreUrls {

        /**
         * GET 类型忽略的资源地址
         */
        private String[] get = { "/webjars/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**"};

        /**
         * POST 类型忽略的资源地址
         */
        private String[] post = {};

        /**
         * PUT 类型忽略的资源地址
         */
        private String[] put = {};

        /**
         * DELETE 类型忽略的资源地址
         */
        private String[] delete = {};

        /**
         * 所有类型忽略的资源地址
         */
        private String[] all = {};
    }
}

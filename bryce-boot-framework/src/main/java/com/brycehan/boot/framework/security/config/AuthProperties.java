package com.brycehan.boot.framework.security.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 认证属性
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.auth")
public class AuthProperties {

    /**
     * Jwt 配置属性
     */
    private Jwt jwt;

    /**
     * 忽略的资源地址
     */
    private IgnoreUrls ignoreUrls;

    @Getter
    @Setter
    public static class Jwt {

        /**
         * 密钥
         */
        private String secret;

        /**
         * 授权key
         */
        private String authoritiesKey = "auth";

        /**
         * token有效期
         */
        private long tokenValidityInSeconds = 7200L;

        /**
         * App token有效期
         */
        private long appTokenValidityInDays = 31L;

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

package com.brycehan.boot.framework.config.properties;

import lombok.Data;
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
     * 忽略的资源地址
     */
    private IgnoreUrls ignoreUrls;


    @Data
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

package com.brycehan.boot.common.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 认证属性
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Data
@Accessors(fluent = true)
@Component
@ConfigurationProperties(prefix = "bryce.auth")
public class AuthProperties {

    /**
     * 忽略的资源地址
     */
    private IgnoreUrls ignoreUrls;


    @Data
    @Accessors(fluent = true)
    public static class IgnoreUrls {

        /**
         * GET 类型忽略的资源地址
         */
        private String[] get;

        /**
         * POST 类型忽略的资源地址
         */
        private String[] post;

        /**
         * PUT 类型忽略的资源地址
         */
        private String[] put;

        /**
         * DELETE 类型忽略的资源地址
         */
        private String[] delete;
    }
}

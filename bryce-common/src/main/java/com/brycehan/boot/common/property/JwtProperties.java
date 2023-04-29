package com.brycehan.boot.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Jwt属性
 *
 * @author Bryce Han
 * @since 2022/5/10
 */
@Data
@EnableConfigurationProperties(JwtProperties.class)
@ConfigurationProperties(prefix = "bryce.jwt")
public class JwtProperties {

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
    private long tokenValidityInSeconds = 2 * 3600L;

}

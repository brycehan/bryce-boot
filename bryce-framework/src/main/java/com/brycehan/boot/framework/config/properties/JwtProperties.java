package com.brycehan.boot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Jwt属性
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Configuration
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

package com.brycehan.boot.mp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信公众号配置属性
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Data
@ConfigurationProperties(prefix = "bryce.mp")
public class MpProperties {

    /**
     * 开关
     */
    private Boolean enabled;
    /**
     * 微信公众号的appid
     */
    private String appId;

    /**
     * 微信公众号的app secret
     */
    private String secret;

    /**
     * 微信公众号的token
     */
    private String token;

    /**
     * 微信公众号的EncodingAESKey
     */
    private String aesKey;

}

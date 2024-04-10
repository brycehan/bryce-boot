package com.brycehan.boot.ma.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信小程序配置属性
 *
 * @author Bryce Han
 * @since 2024/4/6
 */
@Data
@ConfigurationProperties(prefix = "bryce.ma")
public class MaProperties {

    /**
     * 开关
     */
    private Boolean enabled;
    /**
     * 微信小程序的appid
     */
    private String appId;

    /**
     * 微信小程序的app secret
     */
    private String secret;

    /**
     * 微信小程序的token
     */
    private String token;

    /**
     * 微信小程序的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式
     */
    private String msgDataFormat;

}

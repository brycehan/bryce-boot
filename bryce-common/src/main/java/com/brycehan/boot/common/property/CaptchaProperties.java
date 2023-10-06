package com.brycehan.boot.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码属性
 *
 * @since 2022/9/19
 * @author Bryce Han
 */
@Data
@Component
@ConfigurationProperties(prefix = "bryce.captcha")
public class CaptchaProperties {

    /**
     * 验证码宽度
     */
    private int width = 235;

    /**
     * 验证码高度
     */
    private int height = 30;

    /**
     * 验证码内容长度
     */
    private int length = 5;

    /**
     * 验证码有效期（单位：分钟）
     */
    private Long expiration = 5L;
}

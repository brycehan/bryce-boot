package com.brycehan.boot.framework.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
    private int width = 150;

    /**
     * 验证码高度
     */
    private int height = 40;

    /**
     * 字体大小
     */
    private int fontSize = 27;

    /**
     * 验证码内容长度（算术验证码的length表示是几位数运算）
     */
    private int length = 2;

    /**
     * 验证码有效期
     */
    private Duration expiration = Duration.ofMinutes(5);
}

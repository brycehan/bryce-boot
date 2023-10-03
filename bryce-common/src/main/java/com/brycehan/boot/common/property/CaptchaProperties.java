package com.brycehan.boot.common.property;

import com.brycehan.boot.common.enums.CaptchaType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码属性
 *
 * @since 2022/9/19
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.captcha")
public class CaptchaProperties {

    /**
     * 验证码宽度
     */
    private int width = 111;

    /**
     * 验证码高度
     */
    private int height = 36;

    /**
     * 验证码内容长度
     */
    private int length = 2;

    /**
     * 验证码字体
     */
    private String fontName;

    /**
     * 字体大小
     */
    private int fontSize = 25;

    /**
     * 验证码类型
     */
    private CaptchaType captchaType;

    /**
     * 验证码有效期（单位：分钟）
     */
    private Long expiration = 5L;
}

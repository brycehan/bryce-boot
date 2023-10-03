package com.brycehan.boot.framework.config;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.brycehan.boot.common.enums.CaptchaType;
import com.brycehan.boot.common.property.CaptchaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 *
 * @since 2022/9/19
 * @author Bryce Han
 */
@Slf4j
@EnableConfigurationProperties(CaptchaProperties.class)
@Configuration
public class CaptchaConfig {

    /**
     * 验证码生成器
     * 注意：去掉算法的其它类型的有问题
     *
     * @param captchaProperties 配置属性
     * @return 验证码生成器
     */
    @Bean
    public AbstractCaptcha captcha(CaptchaProperties captchaProperties) {
        CaptchaType captchaType = captchaProperties.getCaptchaType();
        AbstractCaptcha captcha = null;
        switch (captchaType) {

            case LINE -> captcha = CaptchaUtil.createLineCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
            case GIF -> captcha = CaptchaUtil.createGifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
            default -> log.error("验证码配置信息错误！");
        }

        return captcha;
    }
}

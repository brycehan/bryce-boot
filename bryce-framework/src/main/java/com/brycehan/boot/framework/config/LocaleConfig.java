package com.brycehan.boot.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 配置国际化语言
 *
 * @author Bryce Han
 * @since 2023/3/20
 */
@Configuration
public class LocaleConfig {

    /**
     * 默认解析器，配置默认语言为中文简体
     *
     * @return 国际化解析器
     */
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }

}

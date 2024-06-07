package com.brycehan.boot.system.common.security.config;

import com.brycehan.boot.api.sms.SmsApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录配置
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Configuration
public class LoginConfig {

    @Bean
    @ConditionalOnMissingBean
    SmsApi smsApi() {
        return (phone, smsType, params) -> false;
    }
}

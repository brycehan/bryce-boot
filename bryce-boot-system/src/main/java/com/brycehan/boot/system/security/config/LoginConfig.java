package com.brycehan.boot.system.security.config;

import com.brycehan.boot.api.sms.SmsApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

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
        return new SmsApi() {
            @Override
            public Boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {
                return false;
            }

            @Override
            public Boolean verifyCode(String phone, String templateId, String code) {
                return false;
            }

            @Override
            public boolean isSmsEnabled() {
                return false;
            }
        };
    }
}

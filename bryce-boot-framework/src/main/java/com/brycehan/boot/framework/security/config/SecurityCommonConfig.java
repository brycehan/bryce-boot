package com.brycehan.boot.framework.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 配置
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Slf4j
@Configuration
public class SecurityCommonConfig {

    /**
     * 使用BCrypt强哈希函数密码加密实现
     *
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

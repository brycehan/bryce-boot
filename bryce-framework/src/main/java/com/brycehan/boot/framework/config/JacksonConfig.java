package com.brycehan.boot.framework.config;

import com.brycehan.boot.framework.xss.XssJacksonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson配置XSS
 *
 * @author Bryce Han
 * @since 2022/5/26
 */
@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class JacksonConfig {

    @Resource
    private ObjectMapper objectMapper;


    @ConditionalOnBean(ObjectMapper.class)
    @PostConstruct
    public void init() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(String.class, new XssJacksonSerializer());
        this.objectMapper.registerModule(simpleModule);
    }

}

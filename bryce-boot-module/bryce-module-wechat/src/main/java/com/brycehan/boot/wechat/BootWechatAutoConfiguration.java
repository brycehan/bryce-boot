package com.brycehan.boot.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BootWechatAutoConfiguration.class)
public class BootWechatAutoConfiguration {

    public BootWechatAutoConfiguration() {
        log.info("Bryce Wechat 自动配置");
    }
}

package com.brycehan.boot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BootQuartzAutoConfiguration.class)
public class BootQuartzAutoConfiguration {

    public BootQuartzAutoConfiguration() {
        log.info("Boot Quartz 自动配置");
    }
}

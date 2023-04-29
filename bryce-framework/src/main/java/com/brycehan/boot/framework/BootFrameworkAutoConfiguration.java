package com.brycehan.boot.framework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackageClasses = BootFrameworkAutoConfiguration.class)
public class BootFrameworkAutoConfiguration {

    public BootFrameworkAutoConfiguration() {
        log.info("Boot Framework 自动配置");
    }
}

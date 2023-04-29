package com.brycehan.boot.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackageClasses = BootSystemAutoConfiguration.class)
public class BootSystemAutoConfiguration {

    public BootSystemAutoConfiguration() {
        log.info("Boot System 自动配置");
    }
}

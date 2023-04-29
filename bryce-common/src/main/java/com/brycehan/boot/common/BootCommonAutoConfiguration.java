package com.brycehan.boot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackageClasses = BootCommonAutoConfiguration.class)
public class BootCommonAutoConfiguration {

    public BootCommonAutoConfiguration() {
        log.info("Boot Common 自动配置");
    }
}

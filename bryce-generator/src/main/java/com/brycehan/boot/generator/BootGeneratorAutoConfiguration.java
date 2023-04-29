package com.brycehan.boot.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BootGeneratorAutoConfiguration.class)
public class BootGeneratorAutoConfiguration {

    public BootGeneratorAutoConfiguration() {
        log.info("Boot Generator 自动配置");
    }
}

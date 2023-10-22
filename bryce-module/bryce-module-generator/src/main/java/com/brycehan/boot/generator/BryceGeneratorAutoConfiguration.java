package com.brycehan.boot.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Generator 自动配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BryceGeneratorAutoConfiguration.class)
public class BryceGeneratorAutoConfiguration {

    public BryceGeneratorAutoConfiguration() {
        log.info("Bryce Generator 自动配置完成");
    }
}

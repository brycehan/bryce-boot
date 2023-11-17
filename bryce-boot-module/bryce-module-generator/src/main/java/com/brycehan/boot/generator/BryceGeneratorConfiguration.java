package com.brycehan.boot.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Generator 配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BryceGeneratorConfiguration.class)
public class BryceGeneratorConfiguration {

    public BryceGeneratorConfiguration() {
        log.info("Bryce Generator 配置完成");
    }
}

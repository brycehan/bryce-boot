package com.brycehan.boot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Quartz 自动配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BryceQuartzAutoConfiguration.class)
public class BryceQuartzAutoConfiguration {

    public BryceQuartzAutoConfiguration() {
        log.info("Bryce Quartz 自动配置完成");
    }
}

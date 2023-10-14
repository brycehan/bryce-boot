package com.brycehan.boot.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Monitor 自动配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BootMonitorAutoConfiguration.class)
public class BootMonitorAutoConfiguration {

    public BootMonitorAutoConfiguration() {
        log.info("Bryce Monitor 自动配置完成");
    }
}

package com.brycehan.boot.framework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Framework 自动配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan
@ServletComponentScan
public class BryceFrameworkAutoConfiguration {

    public BryceFrameworkAutoConfiguration() {
        log.info("Bryce Framework 组件自动配置完成");
    }
}

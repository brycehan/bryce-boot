package com.brycehan.boot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce Common 自动配置
 *
 * @since 2023/10/14
 * @author Bryce Han
 */
@Slf4j
@Configuration
@ComponentScan
public class BryceCommonAutoConfiguration {

    public BryceCommonAutoConfiguration() {
        log.info("Bryce Common 组件自动配置完成");
    }
}

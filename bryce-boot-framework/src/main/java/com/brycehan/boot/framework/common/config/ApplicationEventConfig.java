package com.brycehan.boot.framework.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 应用事件配置
 *
 * @since 2022/2/23
 * @author Bryce Han
 */
@Slf4j
@Configuration
@AutoConfigureAfter(ThreadPoolConfig.class)
public class ApplicationEventConfig {

    /**
     * 配置应用事件广播支持异步执行
     *
     * @param executor 任务线程池
     * @return SimpleApplicationEventMulticaster应用事件广播
     */
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(ThreadPoolExecutor executor) {
        log.info("配置应用事件广播支持异步执行");
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(executor);
        return multicaster;
    }

}

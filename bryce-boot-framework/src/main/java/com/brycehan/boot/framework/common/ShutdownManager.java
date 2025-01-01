package com.brycehan.boot.framework.common;

import com.brycehan.boot.common.util.ThreadUtils;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程池关闭管理器
 *
 * @author Bryce Han
 * @since 2025/1/1
 */
@Slf4j
@Component
@ConditionalOnBean(value = {ExecutorService.class, ScheduledExecutorService.class})
@RequiredArgsConstructor
public class ShutdownManager {

    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 停止自定义的线程池
     */
    @PreDestroy
    public void shutdown() {
        log.info("关闭线程池开始...");
        ThreadUtils.shutdownAndAwaitTermination(executorService);
        ThreadUtils.shutdownAndAwaitTermination(scheduledExecutorService);
        log.info("关闭线程池结束");
    }
}

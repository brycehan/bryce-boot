package com.brycehan.boot.framework.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.scheduling.DelegatingSecurityContextSchedulingTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 *
 * @author Bryce Han
 * @since 2024/6/20
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(TaskExecutionProperties.class)
public class AsyncConfig implements AsyncConfigurer {

    private final TaskExecutionProperties taskExecutionProperties;

    /**
     * 获取c
     *
     * @return 异步线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        TaskExecutionProperties.Pool pool = taskExecutionProperties.getPool();
        // 配置核心线程数
        taskExecutor.setCorePoolSize(pool.getCoreSize());
        // 配置最大线程数
        taskExecutor.setMaxPoolSize(pool.getMaxSize());
        taskExecutor.setKeepAliveSeconds((int) pool.getKeepAlive().toSeconds());
        // 配置队列大小
        taskExecutor.setQueueCapacity(pool.getQueueCapacity());
        //配置线程池中的线程的名称前缀
        taskExecutor.setThreadNamePrefix("brc-async-");
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        taskExecutor.initialize();
        return TtlExecutors.getTtlExecutor(new DelegatingSecurityContextSchedulingTaskExecutor(taskExecutor));
    }

}

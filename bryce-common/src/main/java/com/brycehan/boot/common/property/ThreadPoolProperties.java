package com.brycehan.boot.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置属性
 *
 * @author Bryce Han
 * @since 2022/2/23
 */
@Data
@ConfigurationProperties(prefix = "bryce.thread-pool")
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maximumPoolSize;

    /**
     * 存活时间（单位：秒）
     */
    private int keepAliveTime;

    /**
     * 任务队列的任务数
     */
    private int workQueueSize;

}

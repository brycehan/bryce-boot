package com.brycehan.boot.common.util;

import cn.hutool.extra.spring.SpringUtil;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @since 2022/9/26
 * @author Bryce Han
 */
public class ExecutorUtils {

    private static ScheduledExecutorService executor = SpringUtil.getBean(ScheduledExecutorService.class);

    /**
     * 注：在此线程池中的线程，可以获取servlet中的request对象
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {

        executor.schedule(runnable, 0L, TimeUnit.SECONDS);
    }

}

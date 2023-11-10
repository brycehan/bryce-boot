package com.brycehan.boot.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * 线程工具类
 *
 * @since 2022/10/12
 * @author Bryce Han
 */
@Slf4j
public class Threads {

    /**
     * 停止线程池。
     * 先使用shutdown，停止接收新的任务并尝试完成所有已经存在的任务；
     * 如果超时，则调用shutdownNow，取消在workQueue中Pending的任务，并中断所有阻塞函数；
     * 如果仍然超时，则强制退出；
     * 另对在shutdown时线程本身被调用中断做了处理
     *
     * @param executor 线程池
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     */
    public static void shutdownAndAwaitTermination(ExecutorService executor, long timeout, TimeUnit timeUnit) {
        if (Objects.nonNull(executor) && !executor.isShutdown()) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(timeout, timeUnit)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(timeout, TimeUnit.SECONDS)) {
                        log.info("Executor did not terminate.");
                    }
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 停止线程池。
     * 先使用shutdown，停止接收新的任务并尝试完成所有已经存在的任务；
     * 如果超时，则调用shutdownNow，取消在workQueue中Pending的任务，并中断所有阻塞函数；
     * 如果仍然超时，则强制退出；
     * 另对在shutdown时线程本身被调用中断做了处理
     * 超时时间120秒
     *
     * @param executor 线程池
     */
    public static void shutdownAndAwaitTermination(ExecutorService executor) {
        shutdownAndAwaitTermination(executor, 120, TimeUnit.SECONDS);
    }

    /**
     * 打印线程异常信息
     *
     * @param r 线程实例
     * @param t 异常
     */
    public static void printException(Runnable r, Throwable t) {
        if (Objects.isNull(t) && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException | ExecutionException e) {
                t = e;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            log.error(t.getMessage(), t);
        }
    }

}

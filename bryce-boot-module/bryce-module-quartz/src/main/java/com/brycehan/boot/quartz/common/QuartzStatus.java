package com.brycehan.boot.quartz.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 定时任务状态
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
public enum QuartzStatus {

    /**
     * 暂停
     */
    PAUSE(false),

    /**
     * 正常
     */
    NORMAL(true);

    private final Boolean value;
}

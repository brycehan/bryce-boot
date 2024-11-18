package com.brycehan.boot.quartz.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
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
    PAUSE(0),

    /**
     * 正常
     */
    NORMAL(1);

    @EnumValue
    @JsonValue
    private final Integer value;
}

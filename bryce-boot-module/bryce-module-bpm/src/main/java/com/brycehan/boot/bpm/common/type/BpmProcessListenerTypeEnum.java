package com.brycehan.boot.bpm.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 流程监听器的类型
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@AllArgsConstructor
public enum BpmProcessListenerTypeEnum {

    EXECUTION("execution", "执行监听器"),
    TASK("task", "任务执行器");

    private final String type;
    private final String name;

}

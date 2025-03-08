package com.brycehan.boot.bpm.common.candidate;

import jakarta.validation.constraints.NotNull;

/**
 * 流程任务候选人策略
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
public interface BpmTaskCandidateStrategy {

    /**
     * 获取策略枚举
     *
     * @return 策略枚举
     */
    BpmTaskCandidateStrategyEnum getStrategy();
}

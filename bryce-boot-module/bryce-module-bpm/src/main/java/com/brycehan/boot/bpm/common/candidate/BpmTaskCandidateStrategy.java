package com.brycehan.boot.bpm.common.candidate;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.delegate.DelegateExecution;

import java.util.Map;
import java.util.Set;

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

    /**
     * 校验参数
     *
     * @param param 参数
     */
    void validateParam(String param);

    /**
     * 是否一定要输入参数
     *
     * @return 是否
     */
    default boolean isParamRequired() {
        return true;
    }

    /**
     * 基于候选人参数，获得任务的候选用户们
     * <br/>
     * 注意：实现 calculateUsers 系列方法时，有两种选择：
     * 1. 只重写 calculateUsers 默认方法
     * 2. 都重写 calculateUsersByTask 和 calculateUsersByActivity 两个方法
     *
     * @param param 执行任务
     * @return 用户编号集合
     */
    default Set<Long> calculateUsers(String param) {
        throw new UnsupportedOperationException("该分配方法未实现，请检查！");
    }

    /**
     * 基于[执行任务]，获得任务的候选用户们
     *
     * @param execution 执行任务
     * @return 用户编号集合
     */
    default Set<Long> calculateUsersByTask(DelegateExecution execution, String param) {
        return calculateUsers(param);
    }

    /**
     * 基于[流程活动]，获得任务的候选用户们
     * <p>
     * 目的：用于获取未执行节点的候选用户们
     *
     * @param bpmnModel 流程图
     * @param activityId 活动 ID (对应 Bpmn XML id)
     * @param param     节点的参数
     * @param startUserId  流程发起人编号
     * @param processDefinitionId 流程定义编号
     * @param processVariables 流程变量
     * @return 用户编号集合
     */
    @SuppressWarnings("unused")
    default Set<Long> calculateUsersByActivity(BpmnModel bpmnModel,
                                               String activityId,
                                               String param,
                                               Long startUserId,
                                               String processDefinitionId,
                                               Map<String, Object> processVariables) {
        return calculateUsers(param);
    }
}

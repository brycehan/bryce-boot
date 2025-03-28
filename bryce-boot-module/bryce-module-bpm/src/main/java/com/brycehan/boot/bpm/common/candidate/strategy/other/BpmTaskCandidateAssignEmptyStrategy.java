package com.brycehan.boot.bpm.common.candidate.strategy.other;

import cn.hutool.core.lang.Assert;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import com.brycehan.boot.bpm.common.type.BpmUserTaskAssignEmptyHandlerTypeEnum;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import jakarta.annotation.Resource;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 审批人为空 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/16
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateAssignEmptyStrategy implements BpmTaskCandidateStrategy {

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.ASSIGN_EMPTY;
    }

    @Override
    public void validateParam(String param) {
    }

    @Override
    public Set<Long> calculateUsersByTask(DelegateExecution execution, String param) {
        return getCandidateUsers(execution.getProcessDefinitionId(), execution.getCurrentFlowElement());
    }

    @Override
    public Set<Long> calculateUsersByActivity(BpmnModel bpmnModel, String activityId, String param,
                                              Long startUserId, String processDefinitionId, Map<String, Object> processVariables) {
        FlowElement flowElement = BpmnModelUtils.getFlowElementById(bpmnModel, activityId);
        return getCandidateUsers(processDefinitionId, flowElement);
    }

    private Set<Long> getCandidateUsers(String processDefinitionId, FlowElement flowElement) {
        // 情况一：指定人员审批
        Integer assignEmptyHandlerType = BpmnModelUtils.parseAssignEmptyHandlerType(flowElement);
        if (Objects.equals(assignEmptyHandlerType, BpmUserTaskAssignEmptyHandlerTypeEnum.ASSIGN_USER.getValue())) {
            return new HashSet<>(BpmnModelUtils.parseAssignEmptyHandlerUserIds(flowElement));
        }

        // 情况二：流程管理员
        if (Objects.equals(assignEmptyHandlerType, BpmUserTaskAssignEmptyHandlerTypeEnum.ASSIGN_ADMIN.getValue())) {
            BpmProcessDefinitionInfo processDefinition = bpmProcessDefinitionInfoService.getProcessDefinitionInfo(processDefinitionId);
            Assert.notNull(processDefinition, "流程定义({})不存在", processDefinitionId);
            return new HashSet<>(processDefinition.getManagerUserIds());
        }

        // 都不满足，还是返回空
        return new HashSet<>();
    }

}
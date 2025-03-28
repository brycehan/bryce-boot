package com.brycehan.boot.bpm.common.candidate.strategy.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.common.BpmnVariableConstants;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import com.brycehan.boot.bpm.common.candidate.strategy.user.BpmTaskCandidateUserStrategy;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.google.common.collect.Sets;
import jakarta.annotation.Resource;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.Task;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 发起人自选 {@link BpmTaskCandidateUserStrategy} 实现类
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateStartUserSelectStrategy extends AbstractBpmTaskCandidateDeptLeaderStrategy {

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.START_USER_SELECT;
    }

    @Override
    public void validateParam(String param) {}

    @Override
    public boolean isParamRequired() {
        return false;
    }

    @Override
    public LinkedHashSet<Long> calculateUsersByTask(DelegateExecution execution, String param) {
        ProcessInstance processInstance = processInstanceService.getProcessInstance(execution.getProcessInstanceId());
        Assert.notNull(processInstance, "流程实例({})不能为空", execution.getProcessInstanceId());
        Map<String, List<Long>> startUserSelectAssignees = FlowableUtils.getStartUserSelectAssignees(processInstance);
        Assert.notNull(startUserSelectAssignees, "流程实例({}) 的发起人自选审批人不能为空",
                execution.getProcessInstanceId());
        // 获得审批人
        List<Long> assignees = startUserSelectAssignees.get(execution.getCurrentActivityId());
        return new LinkedHashSet<>(assignees);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LinkedHashSet<Long> calculateUsersByActivity(BpmnModel bpmnModel, String activityId, String param,
                                                        Long startUserId, String processDefinitionId, Map<String, Object> processVariables) {
        if (processVariables == null) {
            return Sets.newLinkedHashSet();
        }
        Map<String, List<Long>> startUserSelectAssignees =  (Map<String, List<Long>>) processVariables.get(BpmnVariableConstants.PROCESS_START_USER_SELECT_ASSIGNEES);
        if (startUserSelectAssignees == null) {
            return Sets.newLinkedHashSet();
        }
        // 获得审批人
        List<Long> assignees = startUserSelectAssignees.get(activityId);
        return new LinkedHashSet<>(assignees);
    }

    /**
     * 获得发起人自选审批人或抄送人的 Task 列表
     *
     * @param bpmnModel BPMN 模型
     * @return Task 列表
     */
    public static List<Task> getStartUserSelectTaskList(BpmnModel bpmnModel) {
        if (bpmnModel == null) {
            return Collections.emptyList();
        }
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(BpmnModelUtils.getFlowElementsOfType(bpmnModel, UserTask.class));
        tasks.addAll(BpmnModelUtils.getFlowElementsOfType(bpmnModel, ServiceTask.class));
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }
        tasks.removeIf(task -> ObjectUtil.notEqual(BpmnModelUtils.parseCandidateStrategy(task), BpmTaskCandidateStrategyEnum.START_USER_SELECT.getValue()));
        return tasks;
    }

}

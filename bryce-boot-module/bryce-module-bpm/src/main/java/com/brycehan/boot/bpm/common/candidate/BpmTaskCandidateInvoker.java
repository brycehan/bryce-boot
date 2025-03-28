package com.brycehan.boot.bpm.common.candidate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.common.type.BpmUserTaskApproveTypeEnum;
import com.brycehan.boot.bpm.common.type.BpmUserTaskAssignStartUserHandlerTypeEnum;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.enums.StatusType;
import com.google.common.annotations.VisibleForTesting;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.*;

/**
 * 流程任务候选人的调用者
 *
 * <br>
 *  用于调用{@link BpmTaskCandidateStrategy}策略，实现任务的候选人的计算
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
public class BpmTaskCandidateInvoker {

    private final Map<BpmTaskCandidateStrategyEnum, BpmTaskCandidateStrategy> strategyMap =new HashMap<>();

    private final BpmUserApi bpmUserApi;

    @SuppressWarnings("all")
    public BpmTaskCandidateInvoker(List<BpmTaskCandidateStrategy> strategies, BpmUserApi bpmUserApi) {
        for (BpmTaskCandidateStrategy bpmTaskCandidateStrategy : strategies) {
            BpmTaskCandidateStrategy candidateStrategy = strategyMap.put(bpmTaskCandidateStrategy.getStrategy(), bpmTaskCandidateStrategy);
//            Assert.isNull(candidateStrategy, StrUtil.format("候选人策略[{}]重复", candidateStrategy.getStrategy()));
        }
        this.bpmUserApi = bpmUserApi;
    }

    public void validateBpmnConfig(byte[] bpmnBytes) {
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(bpmnBytes);
        assert bpmnModel != null;
        List<UserTask> userTaskList = BpmnModelUtils.getFlowElementsOfType(bpmnModel, UserTask.class);
        // 遍历所有的 UserTask，校验审批人配置
        userTaskList.forEach(userTask -> {
            // 1.1 非人工审批，无需校验审批人配置
            Integer approveType = BpmnModelUtils.parseApproveType(userTask);
            if (Objects.equals(approveType, BpmUserTaskApproveTypeEnum.AUTO_APPROVE.getValue())
                    || Objects.equals(approveType, BpmUserTaskApproveTypeEnum.AUTO_REJECT.getValue())) {
                return;
            }
            // 1.2 非空校验
            Integer strategy = BpmnModelUtils.parseCandidateStrategy(userTask);
            String param = BpmnModelUtils.parseCandidateParam(userTask);
            if (strategy == null) {
                throw ServerException.of(BpmResponseStatus.MODEL_TASK_CANDIDATE_NOT_CONFIG, userTask.getName());
            }
            BpmTaskCandidateStrategy candidateStrategy = getCandidateStrategy(strategy);
            if (candidateStrategy.isParamRequired() && StrUtil.isBlank(param)) {
                throw ServerException.of(BpmResponseStatus.MODEL_TASK_CANDIDATE_NOT_CONFIG, userTask.getName());
            }
            // 2. 具体策略校验
            getCandidateStrategy(strategy).validateParam(param);
        });
    }

    /**
     * 计算任务的候选人
     *
     * @param execution 执行任务
     * @return 用户编号集合
     */
    public Set<Long> calculateUsersByTask(DelegateExecution execution) {
        // 注意：解决极端情况下，Flowable 异步调用，导致租户 id 丢失的情况
        // 例如说，SIMPLE 延迟器在 trigger 的时候！！！
        return FlowableUtils.execute(execution.getTenantId(), () -> {
            // 审批类型非人工审核时，不进行计算候选人。原因是：后续会自动通过、不通过
            FlowElement flowElement = execution.getCurrentFlowElement();
            Integer approveType = BpmnModelUtils.parseApproveType(flowElement);
            if (Objects.equals(approveType, BpmUserTaskApproveTypeEnum.AUTO_APPROVE.getValue())
                    || Objects.equals(approveType, BpmUserTaskApproveTypeEnum.AUTO_REJECT.getValue())) {
                return new HashSet<>();
            }

            // 1.1 计算任务的候选人
            Integer strategy = BpmnModelUtils.parseCandidateStrategy(flowElement);
            String param = BpmnModelUtils.parseCandidateParam(flowElement);
            Set<Long> userIds = getCandidateStrategy(strategy).calculateUsersByTask(execution, param);
            // 1.2 移除被禁用的用户
            removeDisableUsers(userIds);

            // 2. 候选人为空时，根据“审批人为空”的配置补充
            if (CollUtil.isEmpty(userIds)) {
                userIds = getCandidateStrategy(BpmTaskCandidateStrategyEnum.ASSIGN_EMPTY.getValue())
                        .calculateUsersByTask(execution, param);
                // ASSIGN_EMPTY 策略，不需要移除被禁用的用户。原因是，再移除，可能会出现更没审批人了！！！
            }

            // 3. 移除发起人的用户
            ProcessInstance processInstance = SpringUtil.getBean(BpmProcessInstanceService.class)
                    .getProcessInstance(execution.getProcessInstanceId());
            cn.hutool.core.lang.Assert.notNull(processInstance, "流程实例({}) 不存在", execution.getProcessInstanceId());
            removeStartUserIfSkip(userIds, flowElement, Long.valueOf(processInstance.getStartUserId()));
            return userIds;
        });
    }

    public Set<Long> calculateUsersByActivity(BpmnModel bpmnModel, String activityId,
                                              Long startUserId, String processDefinitionId, Map<String, Object> processVariables) {
        // 审批类型非人工审核时，不进行计算候选人。原因是：后续会自动通过、不通过
        FlowElement flowElement = BpmnModelUtils.getFlowElementById(bpmnModel, activityId);
        Integer approveType = BpmnModelUtils.parseApproveType(flowElement);
        if (Objects.equals(approveType, BpmUserTaskApproveTypeEnum.AUTO_APPROVE.getValue())
                || Objects.equals(approveType, BpmUserTaskApproveTypeEnum.AUTO_REJECT.getValue())) {
            return new HashSet<>();
        }

        // 1.1 计算任务的候选人
        Integer strategy = BpmnModelUtils.parseCandidateStrategy(flowElement);
        String param = BpmnModelUtils.parseCandidateParam(flowElement);
        Set<Long> userIds = getCandidateStrategy(strategy).calculateUsersByActivity(bpmnModel, activityId, param,
                startUserId, processDefinitionId, processVariables);
        // 1.2 移除被禁用的用户
        removeDisableUsers(userIds);

        // 2. 候选人为空时，根据“审批人为空”的配置补充
        if (CollUtil.isEmpty(userIds)) {
            userIds = getCandidateStrategy(BpmTaskCandidateStrategyEnum.ASSIGN_EMPTY.getValue())
                    .calculateUsersByActivity(bpmnModel, activityId, param, startUserId, processDefinitionId, processVariables);
            // ASSIGN_EMPTY 策略，不需要移除被禁用的用户。原因是，再移除，可能会出现更没审批人了！！！
        }

        // 3. 移除发起人的用户
        removeStartUserIfSkip(userIds, flowElement, startUserId);
        return userIds;
    }

    @VisibleForTesting
    void removeDisableUsers(Set<Long> assigneeUserIds) {
        if (CollUtil.isEmpty(assigneeUserIds)) {
            return;
        }
        Map<Long, BpmUserVo> userMap = bpmUserApi.getUserMap(List.copyOf(assigneeUserIds));
        assigneeUserIds.removeIf(id -> {
            BpmUserVo user = userMap.get(id);
            return user == null || StatusType.DISABLE.equals(user.getStatus());
        });
    }

    /**
     * 如果“审批人与发起人相同时”，配置了 SKIP 跳过，则移除发起人
     * <br>
     * 注意：如果只有一个候选人，则不处理，避免无法审批
     *
     * @param assigneeUserIds 当前分配的候选人
     * @param flowElement 当前节点
     * @param startUserId 发起人
     */
    @VisibleForTesting
    void removeStartUserIfSkip(Set<Long> assigneeUserIds, FlowElement flowElement, Long startUserId) {
        if (CollUtil.size(assigneeUserIds) <= 1) {
            return;
        }
        Integer assignStartUserHandlerType = BpmnModelUtils.parseAssignStartUserHandlerType(flowElement);
        if (ObjectUtil.notEqual(assignStartUserHandlerType, BpmUserTaskAssignStartUserHandlerTypeEnum.SKIP.getValue())) {
            return;
        }
        assigneeUserIds.remove(startUserId);
    }

    private BpmTaskCandidateStrategy getCandidateStrategy(Integer strategy) {
        BpmTaskCandidateStrategyEnum strategyEnum = BpmTaskCandidateStrategyEnum.of(strategy);
        cn.hutool.core.lang.Assert.notNull(strategyEnum, "策略(%s) 不存在", strategy);
        BpmTaskCandidateStrategy strategyObj = strategyMap.get(strategyEnum);
        cn.hutool.core.lang.Assert.notNull(strategyObj, "策略(%s) 不存在", strategy);
        return strategyObj;
    }
}

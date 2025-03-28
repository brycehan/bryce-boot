package com.brycehan.boot.bpm.common.candidate.strategy.dept;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import jakarta.annotation.Resource;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 发起人连续多级部门的负责人 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateStartUserDeptLeaderMultiStrategy extends AbstractBpmTaskCandidateDeptLeaderStrategy {

    @Resource
    @Lazy
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.START_USER_DEPT_LEADER_MULTI;
    }

    @Override
    public void validateParam(String param) {
        int level = Integer.parseInt(param); // 参数是部门的层级
        Assert.isTrue(level > 0, "部门的层级必须大于 0");
    }

    @Override
    public Set<Long> calculateUsersByTask(DelegateExecution execution, String param) {
        int level = Integer.parseInt(param); // 参数是部门的层级
        // 获得流程发起人
        ProcessInstance processInstance = processInstanceService.getProcessInstance(execution.getProcessInstanceId());
        Long startUserId = NumberUtil.parseLong(processInstance.getStartUserId(), null);
        // 获取发起人的 multi 部门负责人
        BpmDeptVo dept = super.getStartUserDept(startUserId);
        if (dept == null) {
            return new HashSet<>();
        }
        return super.getMultiLevelDeptLeaderIds(ListUtil.toList(dept.getId()), level);
    }

    @Override
    public Set<Long> calculateUsersByActivity(BpmnModel bpmnModel, String activityId, String param,
                                              Long startUserId, String processDefinitionId, Map<String, Object> processVariables) {
        int level = Integer.parseInt(param); // 参数是部门的层级
        BpmDeptVo dept = super.getStartUserDept(startUserId);
        if (dept == null) {
            return new HashSet<>();
        }
        return super.getMultiLevelDeptLeaderIds(ListUtil.toList(dept.getId()), level);
    }

}

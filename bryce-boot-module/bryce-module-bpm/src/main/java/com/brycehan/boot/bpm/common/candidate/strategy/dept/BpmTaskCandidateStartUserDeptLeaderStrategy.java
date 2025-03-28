package com.brycehan.boot.bpm.common.candidate.strategy.dept;

import cn.hutool.core.collection.CollUtil;
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
 * 发起人的部门负责人, 可以是上级部门负责人 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateStartUserDeptLeaderStrategy extends AbstractBpmTaskCandidateDeptLeaderStrategy {

    @Resource
    @Lazy // 避免循环依赖
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.START_USER_DEPT_LEADER;
    }

    @Override
    public void validateParam(String param) {
        // 参数是部门的层级
        Assert.isTrue(Integer.parseInt(param) > 0, "部门的层级必须大于 0");
    }

    @Override
    public Set<Long> calculateUsersByTask(DelegateExecution execution, String param) {
        // 获得流程发起人
        ProcessInstance processInstance = processInstanceService.getProcessInstance(execution.getProcessInstanceId());
        Long startUserId = NumberUtil.parseLong(processInstance.getStartUserId(), null);
        // 获取发起人的部门负责人
        return getStartUserDeptLeader(startUserId, param);
    }

    @Override
    public Set<Long> calculateUsersByActivity(BpmnModel bpmnModel, String activityId, String param,
                                              Long startUserId, String processDefinitionId, Map<String, Object> processVariables) {
        // 获取发起人的部门负责人
        return getStartUserDeptLeader(startUserId, param);
    }

    private Set<Long> getStartUserDeptLeader(Long startUserId, String param) {
        int level = Integer.parseInt(param); // 参数是部门的层级
        BpmDeptVo dept = super.getStartUserDept(startUserId);
        if (dept == null) {
            return new HashSet<>();
        }
        Long deptLeaderId = super.getAssignLevelDeptLeaderId(dept, level);
        return deptLeaderId != null ? CollUtil.newHashSet(deptLeaderId) : new HashSet<>();
    }

}

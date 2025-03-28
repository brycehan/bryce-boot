package com.brycehan.boot.bpm.common.candidate.strategy.dept;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 连续多级部门的负责人 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateDeptLeaderMultiStrategy extends AbstractBpmTaskCandidateDeptLeaderStrategy {

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.MULTI_DEPT_LEADER_MULTI;
    }

    @Override
    public void validateParam(String param) {
        // 参数格式: | 分隔：1）左边为部门（多个部门用 , 分隔）。2）右边为部门层级
        String[] params = param.split("\\|");
        Assert.isTrue(params.length == 2, "参数格式不匹配");
        List<Long> deptIds = Arrays.stream(StrUtil.splitToLong(params[0], ",")).boxed().toList();
        int level = Integer.parseInt(params[1]);
        // 校验部门存在
        bpmDeptApi.validateDeptList(deptIds);
        Assert.isTrue(level > 0, "部门层级必须大于 0");
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        String[] params = param.split("\\|");
        List<Long> deptIds = Arrays.stream(StrUtil.splitToLong(params[0], ",")).boxed().toList();
        int level = Integer.parseInt(params[1]);
        return super.getMultiLevelDeptLeaderIds(deptIds, level);
    }

}

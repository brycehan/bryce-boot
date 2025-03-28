package com.brycehan.boot.bpm.common.candidate.strategy.dept;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.BpmDeptApi;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门的负责人 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateDeptLeaderStrategy implements BpmTaskCandidateStrategy {


    @Resource
    private BpmDeptApi deptApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.DEPT_LEADER;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> deptIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().collect(Collectors.toSet());
        deptApi.validateDeptList(deptIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> deptIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().collect(Collectors.toSet());
        List<BpmDeptVo> depts = deptApi.getDeptList(deptIds);
        return depts.stream().map(BpmDeptVo::getLeaderUserId).collect(Collectors.toSet());
    }

}
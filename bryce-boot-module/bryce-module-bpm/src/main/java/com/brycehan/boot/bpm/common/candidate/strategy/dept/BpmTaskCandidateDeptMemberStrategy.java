package com.brycehan.boot.bpm.common.candidate.strategy.dept;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.BpmDeptApi;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门的成员 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateDeptMemberStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private BpmDeptApi deptApi;
    @Resource
    private BpmUserApi adminUserApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.DEPT_MEMBER;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> deptIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().collect(Collectors.toSet());
        deptApi.validateDeptList(deptIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        List<Long> deptIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().toList();
        List<BpmUserVo> users = adminUserApi.getUsersByDeptIds(deptIds);
        return users.stream().map(BpmUserVo::getId).collect(Collectors.toSet());
    }

}
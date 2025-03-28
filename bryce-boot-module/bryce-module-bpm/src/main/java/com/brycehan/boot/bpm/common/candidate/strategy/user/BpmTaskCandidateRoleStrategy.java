package com.brycehan.boot.bpm.common.candidate.strategy.user;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.BpmRoleApi;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/28
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateRoleStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private BpmRoleApi roleApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.ROLE;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> roleIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().collect(Collectors.toSet());
        roleApi.validateRoleList(roleIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        List<Long> roleIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().toList();
        return new HashSet<>(roleApi.getUserIdsByRoleIds(roleIds));
    }

}
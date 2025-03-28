package com.brycehan.boot.bpm.common.candidate.strategy.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import com.brycehan.boot.bpm.entity.po.BpmUserGroup;
import com.brycehan.boot.bpm.service.BpmUserGroupService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户组 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
@Component
public class BpmTaskCandidateGroupStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private BpmUserGroupService userGroupService;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.USER_GROUP;
    }

    @Override
    public void validateParam(String param) {
        long[] ids = StrUtil.splitToLong(param, ",");
        Set<Long> groupIds = Arrays.stream(ids).boxed().collect(Collectors.toSet());

        userGroupService.validUserGroups(groupIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        long[] ids = StrUtil.splitToLong(param, ",");
        Set<Long> groupIds = Arrays.stream(ids).boxed().collect(Collectors.toSet());

        List<BpmUserGroup> groups = userGroupService.listByIds(groupIds);
        if (CollUtil.isEmpty(groups)) {
            return Collections.emptySet();
        }
        return groups.stream().map(BpmUserGroup::getUserIds).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toSet());
    }

}
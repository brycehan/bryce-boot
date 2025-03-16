package com.brycehan.boot.bpm.common.candidate.strategy;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategy;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/16
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidateUserStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private BpmUserApi adminUserApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.USER;
    }

    @Override
    public void validateParam(String param) {
        long[] ids = StrUtil.splitToLong(param, StrPool.COMMA);
        List<Long> userIds = Arrays.stream(ids).boxed().toList();
        adminUserApi.validateUsers(userIds);
    }

    @Override
    public LinkedHashSet<Long> calculateUsers(String param) {
        long[] ids = StrUtil.splitToLong(param, StrPool.COMMA);
        return Arrays.stream(ids).boxed().collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
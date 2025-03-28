package com.brycehan.boot.bpm.common.candidate.strategy.user;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.BpmPostApi;
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
 * 岗位 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @since 2025/3/28
 * @author Bryce Han
 */
@Component
public class BpmTaskCandidatePostStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private BpmPostApi postApi;
    @Resource
    private BpmUserApi adminUserApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.POST;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> postIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().collect(Collectors.toSet());
        postApi.validatePostList(postIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        List<Long> postIds = Arrays.stream(StrUtil.splitToLong(param, StrPool.COMMA)).boxed().toList();
        List<BpmUserVo> users = adminUserApi.getUsersByPostIds(postIds);
        return users.stream().map(BpmUserVo::getId).collect(Collectors.toSet());
    }

}
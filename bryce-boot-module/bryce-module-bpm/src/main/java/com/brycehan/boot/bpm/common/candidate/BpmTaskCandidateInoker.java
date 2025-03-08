package com.brycehan.boot.bpm.common.candidate;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 流程任务候选人的调用者
 *
 * <br>
 *  用于调用{@link BpmTaskCandidateStrategy}策略，实现任务的候选人的计算
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
public class BpmTaskCandidateInoker {

    private final Map<BpmTaskCandidateStrategyEnum, BpmTaskCandidateStrategy> strategyMap =new HashMap<>();

    private final BpmUserApi bpmUserApi;

    @SuppressWarnings("all")
    public BpmTaskCandidateInoker(List<BpmTaskCandidateStrategy> strategies, BpmUserApi bpmUserApi) {
        for (BpmTaskCandidateStrategy bpmTaskCandidateStrategy : strategies) {
            BpmTaskCandidateStrategy candidateStrategy = strategyMap.put(bpmTaskCandidateStrategy.getStrategy(), bpmTaskCandidateStrategy);
            Assert.isNull(candidateStrategy, StrUtil.format("候选人策略[{}]重复", candidateStrategy.getStrategy()));
        }
        this.bpmUserApi = bpmUserApi;
    }

    public void validateBpmnConfig(byte[] bpmnBytes) {
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(bpmnBytes);
    }
}

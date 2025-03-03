package com.brycehan.boot.bpm.common.candidate;

import com.brycehan.boot.bpm.common.BpmnModelUtils;
import org.flowable.bpmn.model.BpmnModel;

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

    public void validateBpmnConfig(byte[] bpmnBytes) {
//        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(bpmnBytes);
    }
}

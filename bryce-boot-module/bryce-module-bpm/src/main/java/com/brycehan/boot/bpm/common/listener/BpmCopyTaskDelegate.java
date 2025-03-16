package com.brycehan.boot.bpm.common.listener;

import cn.hutool.core.collection.CollUtil;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateInvoker;
import jakarta.annotation.Resource;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 处理抄送用户的 {@link JavaDelegate} 的实现类
 * <p>
 * 目前只有仿钉钉/飞书模式的【抄送节点】使用
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Component(BpmCopyTaskDelegate.BEAN_NAME)
public class BpmCopyTaskDelegate implements JavaDelegate {

    public static final String BEAN_NAME = "bpmCopyTaskDelegate";

    @Resource
    private BpmTaskCandidateInvoker taskCandidateInvoker;

//    @Resource
//    private BpmProcessInstanceCopyService processInstanceCopyService;

    @Override
    public void execute(DelegateExecution execution) {
        // 1. 获得抄送人
        Set<Long> userIds = taskCandidateInvoker.calculateUsersByTask(execution);
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        // 2. 执行抄送
        FlowElement currentFlowElement = execution.getCurrentFlowElement();
//        processInstanceCopyService.createProcessInstanceCopy(userIds, null, execution.getProcessInstanceId(),
//                currentFlowElement.getId(), currentFlowElement.getName(), null);
    }

}

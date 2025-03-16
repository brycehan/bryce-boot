package com.brycehan.boot.bpm.service;

import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenProcessInstanceApproveDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenProcessInstanceRejectDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenTaskCreatedDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenTaskTimeoutDto;
import jakarta.validation.Valid;

/**
 * BPM 消息 Service 接口
 * <br>
 * 未来支持消息的可配置；不同的流程，在什么场景下，需要发送什么消息，消息的内容是什么；
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
public interface BpmMessageService {

    /**
     * 发送流程实例被通过的消息
     *
     * @param bpmMessageSendWhenProcessInstanceApproveDto 发送信息
     */
    void sendMessageWhenProcessInstanceApprove(@Valid BpmMessageSendWhenProcessInstanceApproveDto bpmMessageSendWhenProcessInstanceApproveDto);

    /**
     * 发送流程实例被不通过的消息
     *
     * @param bpmMessageSendWhenProcessInstanceRejectDto 发送信息
     */
    void sendMessageWhenProcessInstanceReject(@Valid BpmMessageSendWhenProcessInstanceRejectDto bpmMessageSendWhenProcessInstanceRejectDto);

    /**
     * 发送任务被分配的消息
     *
     * @param bpmMessageSendWhenTaskCreatedDto 发送信息
     */
    void sendMessageWhenTaskAssigned(@Valid BpmMessageSendWhenTaskCreatedDto bpmMessageSendWhenTaskCreatedDto);

    /**
     * 发送任务审批超时的消息
     *
     * @param bpmMessageSendWhenTaskTimeoutDto 发送信息
     */
    void sendMessageWhenTaskTimeout(@Valid BpmMessageSendWhenTaskTimeoutDto bpmMessageSendWhenTaskTimeoutDto);

}

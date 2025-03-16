package com.brycehan.boot.bpm.service.impl;

import com.brycehan.boot.api.sms.SmsApi;
import com.brycehan.boot.api.system.SysUserApi;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenProcessInstanceApproveDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenProcessInstanceRejectDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenTaskCreatedDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenTaskTimeoutDto;
import com.brycehan.boot.bpm.service.BpmMessageService;
import com.brycehan.boot.common.enums.SmsType;
import com.brycehan.boot.framework.common.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * BPM 消息 Service 实现类
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BpmMessageServiceImpl implements BpmMessageService {

    private final SmsApi smsApi;
    private final SysUserApi sysUserApi;
    private final ApplicationProperties applicationProperties;

    @Override
    public void sendMessageWhenProcessInstanceApprove(BpmMessageSendWhenProcessInstanceApproveDto bpmMessageSendWhenProcessInstanceApproveDto) {
        LinkedHashMap<String, String> templateParams = new LinkedHashMap<>();
        templateParams.put("processInstanceName", bpmMessageSendWhenProcessInstanceApproveDto.getProcessInstanceName());
        templateParams.put("detailUrl", getProcessInstanceDetailUrl(bpmMessageSendWhenProcessInstanceApproveDto.getProcessInstanceId()));

        String phone = sysUserApi.getUserPhoneById(bpmMessageSendWhenProcessInstanceApproveDto.getStartUserId());
        smsApi.send(phone, SmsType.PROCESS_INSTANCE_APPROVE, templateParams);
    }

    @Override
    public void sendMessageWhenProcessInstanceReject(BpmMessageSendWhenProcessInstanceRejectDto bpmMessageSendWhenProcessInstanceRejectDto) {
        LinkedHashMap<String, String> templateParams = new LinkedHashMap<>();
        templateParams.put("processInstanceName", bpmMessageSendWhenProcessInstanceRejectDto.getProcessInstanceName());
        templateParams.put("reason", bpmMessageSendWhenProcessInstanceRejectDto.getReason());
        templateParams.put("detailUrl", getProcessInstanceDetailUrl(bpmMessageSendWhenProcessInstanceRejectDto.getProcessInstanceId()));

        String phone = sysUserApi.getUserPhoneById(bpmMessageSendWhenProcessInstanceRejectDto.getStartUserId());
        smsApi.send(phone, SmsType.PROCESS_INSTANCE_REJECT, templateParams);
    }

    @Override
    public void sendMessageWhenTaskAssigned(BpmMessageSendWhenTaskCreatedDto bpmMessageSendWhenTaskCreatedDto) {
        LinkedHashMap<String, String> templateParams = new LinkedHashMap<>();
        templateParams.put("processInstanceName", bpmMessageSendWhenTaskCreatedDto.getProcessInstanceName());
        templateParams.put("taskName", bpmMessageSendWhenTaskCreatedDto.getTaskName());
        templateParams.put("startUserNickname", bpmMessageSendWhenTaskCreatedDto.getStartUserNickname());
        templateParams.put("detailUrl", getProcessInstanceDetailUrl(bpmMessageSendWhenTaskCreatedDto.getProcessInstanceId()));

        String phone = sysUserApi.getUserPhoneById(bpmMessageSendWhenTaskCreatedDto.getAssigneeUserId());

        smsApi.send(phone, SmsType.TASK_ASSIGNED, templateParams);
    }

    @Override
    public void sendMessageWhenTaskTimeout(BpmMessageSendWhenTaskTimeoutDto bpmMessageSendWhenTaskTimeoutDto) {
        LinkedHashMap<String, String> templateParams = new LinkedHashMap<>();
        templateParams.put("processInstanceName", bpmMessageSendWhenTaskTimeoutDto.getProcessInstanceName());
        templateParams.put("taskName", bpmMessageSendWhenTaskTimeoutDto.getTaskName());
        templateParams.put("detailUrl", getProcessInstanceDetailUrl(bpmMessageSendWhenTaskTimeoutDto.getProcessInstanceId()));
        String phone = sysUserApi.getUserPhoneById(bpmMessageSendWhenTaskTimeoutDto.getAssigneeUserId());

        smsApi.send(phone, SmsType.TASK_TIMEOUT, templateParams);
    }

    private String getProcessInstanceDetailUrl(String taskId) {
        return applicationProperties.getEndpoint() + "/bpm/process-instance/detail?id=" + taskId;
    }

}

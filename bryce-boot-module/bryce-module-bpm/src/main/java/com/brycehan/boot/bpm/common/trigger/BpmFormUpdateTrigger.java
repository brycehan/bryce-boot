package com.brycehan.boot.bpm.common.trigger;

import cn.hutool.core.collection.CollUtil;
import com.brycehan.boot.bpm.common.type.BpmTriggerTypeEnum;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.common.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BPM 更新流程表单触发器
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Component
@Slf4j
public class BpmFormUpdateTrigger implements BpmTrigger {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTriggerTypeEnum getType() {
        return BpmTriggerTypeEnum.UPDATE_NORMAL_FORM;
    }

    @Override
    public void execute(String processInstanceId, String param) {
        // 1. 解析更新流程表单配置
        BpmSimpleModelNodeVo.TriggerSetting.NormalFormTriggerSetting setting = JsonUtils.readValue(param, BpmSimpleModelNodeVo.TriggerSetting.NormalFormTriggerSetting.class);
        if (setting == null) {
            log.error("[execute][流程({}) 更新流程表单触发器配置为空]", processInstanceId);
            return;
        }
        // 2.更新流程变量
        if (CollUtil.isNotEmpty(setting.getUpdateFormFields())) {
            processInstanceService.updateProcessInstanceVariables(processInstanceId, setting.getUpdateFormFields());
        }
    }

}

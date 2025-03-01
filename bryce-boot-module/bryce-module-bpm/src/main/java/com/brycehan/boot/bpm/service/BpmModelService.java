package com.brycehan.boot.bpm.service;

import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;
import com.brycehan.boot.common.entity.PageResult;

/**
 * 流程定义信息服务
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
public interface BpmModelService {

    /**
     * 添加流程定义信息
     *
     * @param bpmModelDto 流程定义信息Dto
     */
    void save(BpmModelDto bpmModelDto);

    /**
     * 更新流程定义信息
     *
     * @param bpmModelDto 流程定义信息Dto
     */
    void update(BpmModelDto bpmModelDto);

    /**
     * 更新流程定义信息
     *
     * @param id          流程定义信息ID
     * @param bpmnXml     BPMN XML
     */
    void updateModelBpmnXml(String id, String bpmnXml);
}

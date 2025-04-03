package com.brycehan.boot.bpm.service;

import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelPageDto;
import com.brycehan.boot.bpm.entity.vo.BpmModelVo;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import com.brycehan.boot.common.entity.PageResult;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

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
    String save(BpmModelDto bpmModelDto);

    /**
     * 更新流程定义信息
     *
     * @param bpmModelDto 流程定义信息Dto
     */
    void update(BpmModelDto bpmModelDto);

    /**
     * 删除模型信息
     *
     * @param ids IDs
     */
    void delete(List<String> ids);

    /**
     * 根据模型ID查询模型详情
     *
     * @param id 模型ID
     * @return 模型信息
     */
    BpmModelVo getById(String id);

    /**
     * 流程模型分页查询
     *
     * @param bpmModelPageDto 流程模型分页Dto
     */
    PageResult<BpmModelVo> page(BpmModelPageDto bpmModelPageDto);

    /**
     * 更新流程定义信息
     *
     * @param id          流程定义信息ID
     * @param bpmnXml     BPMN XML
     */
    void updateModelBpmnXml(String id, String bpmnXml);

    /**
     * 获取流程定义信息
     *
     * @param id 模型ID
     * @return BPMN XML
     */
    byte[] getModelBpmnXml(String id);

    /**
     * 获取仿钉钉流程设计模型
     *
     * @param id 模型ID
     * @return 仿钉钉流程设计模型
     */
    BpmSimpleModelNodeVo getSimpleModel(String id);

    /**
     * 部署流程模型
     *
     * @param id 模型ID
     */
    void deploy(@NotEmpty String id);

    /**
     * 更新流程定义状态
     *
     * @param id   模型ID
     * @param state 状态
     */
    void updateState(String id, Integer state);
}

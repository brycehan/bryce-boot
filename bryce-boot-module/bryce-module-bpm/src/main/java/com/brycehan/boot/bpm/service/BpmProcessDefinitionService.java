package com.brycehan.boot.bpm.service;

import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionPageDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.vo.BpmModelMetaInfoVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import com.brycehan.boot.common.entity.PageResult;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 流程定义服务
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
public interface BpmProcessDefinitionService {

    /**
     * 获取部署 Map
     *
     * @param deploymentIds 部署ids
     */
    List<Deployment> getDeployments(List<String> deploymentIds);

    /**
     * 获取部署 Map
     *
     * @param deploymentIds 部署ids
     */
    Map<String, Deployment> getDeploymentMap(List<String> deploymentIds);

    /**
     * 获取流程定义
     *
     * @param deploymentId 流程部署id
     */
    ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId);

    /**
     * 获取流程定义列表
     *
     * @param deploymentIds 流程部署ids
     */
    List<ProcessDefinition> getProcessDefinitionsByDeploymentIds(List<String> deploymentIds);

    /**
     * 获取流程定义
     *
     * @param processDefinitionId 流程定义id
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);

    /**
     * 获取流程定义列表
     *
     * @param processDefinitionIds 流程定义ids
     */
    List<ProcessDefinition> getProcessDefinitions(List<String> processDefinitionIds);

    /**
     * 获取流程定义 Map
     *
     * @param processDefinitionIds 流程定义ids
     */
    default Map<String, ProcessDefinition> getProcessDefinitionMap(List<String> processDefinitionIds) {
        return getProcessDefinitions(processDefinitionIds).stream().collect(Collectors.toMap(ProcessDefinition::getId, Function.identity()));
    }

    /**
     * 部署流程定义
     *
     * @param model 流程模型
     * @param metaInfo 流程元信息
     * @param bpmnXml 流程xml
     * @param simpleModelJson 简单流程json
     * @param bpmForm 表单
     */
    ProcessDefinition deploy(Model model, BpmModelMetaInfoVo metaInfo, byte[] bpmnXml, String simpleModelJson, BpmForm bpmForm);

    /**
     * 更新流程定义状态
     *
     * @param id  流程定义id
     * @param status 状态
     */
    void updateProcessDefinitionStatus(String id, boolean status);

    /**
     * 更新流程定义为挂起状态
     *
     * @param deploymentId 流程部署id
     */
    void updateProcessDefinitionSuspended(String deploymentId);

    /**
     * 获取流程定义
     *
     * @param id 流程定义id
     * @return 流程定义
     */
    BpmProcessDefinitionVo getById(String id);

    /**
     * 流程定义分页查询
     *
     * @param bpmProcessDefinitionPageDto 流程定义分页查询参数
     * @return 流程定义分页查询结果
     */
    PageResult<BpmProcessDefinitionVo> page(BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto);

    /**
     * 流程定义列表查询
     *
     * @param bpmProcessDefinitionPageDto 流程定义分页查询参数
     * @return 流程定义分页查询结果
     */
    List<BpmProcessDefinitionVo> list(BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto);
}

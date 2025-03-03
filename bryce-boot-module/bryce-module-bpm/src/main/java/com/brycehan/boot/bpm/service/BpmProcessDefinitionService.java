package com.brycehan.boot.bpm.service;

import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 更新流程定义状态
     *
     * @param id  流程定义id
     * @param status 状态
     */
    void updateProcessDefinitionStatus(String id, boolean status);
}

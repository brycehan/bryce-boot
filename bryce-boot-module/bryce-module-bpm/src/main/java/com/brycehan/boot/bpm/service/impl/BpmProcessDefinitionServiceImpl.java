package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 流程定义服务实现
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmProcessDefinitionServiceImpl implements BpmProcessDefinitionService {

    private final RepositoryService repositoryService;

    @Override
    public List<Deployment> getDeployments(List<String> deploymentIds) {
        return repositoryService.createDeploymentQuery().deploymentIds(deploymentIds).list();
    }

    @Override
    public Map<String, Deployment> getDeploymentMap(List<String> deploymentIds) {
        List<Deployment> deployments = getDeployments(deploymentIds);
        return deployments.stream().collect(Collectors.toMap(Deployment::getId, d -> d));
    }

    @Override
    public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId) {
        if (deploymentId != null) {
            return repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        }
        return null;
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionsByDeploymentIds(List<String> deploymentIds) {
        if (CollUtil.isEmpty(CollUtil.newHashSet(deploymentIds))) {
            return List.of();
        }

        return repositoryService.createProcessDefinitionQuery().deploymentIds(CollUtil.newHashSet(deploymentIds)).list();
    }

    @Override
    public void updateProcessDefinitionStatus(String id, boolean status) {
        repositoryService.activateProcessDefinitionById(id, status, new Date());
    }
}

package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.brycehan.boot.bpm.common.BpmnModelConstants;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.entity.convert.BpmProcessDefinitionConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionPageDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmModelMetaInfoVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import com.brycehan.boot.bpm.service.BpmCategoryService;
import com.brycehan.boot.bpm.service.BpmFormService;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionService;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
    private final BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;
    private final BpmCategoryService bpmCategoryService;
    private final BpmFormService bpmFormService;

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
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitions(List<String> processDefinitionIds) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionIds(Set.copyOf(processDefinitionIds)).list();
    }

    @Override
    public ProcessDefinition deploy(Model model, BpmModelMetaInfoVo metaInfo, byte[] bpmnXml, String simpleModelJson, BpmForm bpmForm) {
        // 部署流程
        Deployment deployment = repositoryService.createDeployment()
                .name(model.getName()).key(model.getKey()).category(model.getCategory())
                .addBytes(model.getKey() + BpmnModelConstants.BPMN_FILE_EXTENSION, bpmnXml)
                .disableSchemaValidation() // 禁用XML Schema 校验，因为有自定义的属性
                .deploy();

        // 设置流程定义的分类
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        repositoryService.setProcessDefinitionCategory(processDefinition.getId(), model.getCategory());

        // 注意 1，ProcessDefinition 的 key 和 name 是通过 BPMN 中的 <bpmn2:process /> 的 id 和 name 决定
        // 注意 2，目前该项目的设计上，需要保证 Model、Deployment、ProcessDefinition 使用相同的 key，保证关联性。
        // 否则，会导致 ProcessDefinition 的分页无法查询到。
        if (!processDefinition.getKey().equals(model.getKey())) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_KEY_NOT_MATCH);
        }
        if (!processDefinition.getName().equals(model.getName())) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_NAME_NOT_MATCH);
        }

        // 插入扩展表
        BpmProcessDefinitionInfo bpmProcessDefinitionInfo = BeanUtil.toBean(metaInfo, BpmProcessDefinitionInfo.class);
        bpmProcessDefinitionInfo.setId(IdGenerator.nextId());
        bpmProcessDefinitionInfo.setModelId(model.getId());
        bpmProcessDefinitionInfo.setProcessDefinitionId(processDefinition.getId());
        bpmProcessDefinitionInfo.setModelType(metaInfo.getType());
        bpmProcessDefinitionInfo.setSimpleModel(simpleModelJson);

        if (bpmForm != null) {
            bpmProcessDefinitionInfo.setFormConf(bpmForm.getConf());
            bpmProcessDefinitionInfo.setFormFields(bpmForm.getFields());
        }

        bpmProcessDefinitionInfoService.save(bpmProcessDefinitionInfo);

        return processDefinition;
    }

    @Override
    public void updateProcessDefinitionStatus(String id, boolean status) {
        if (status) {
            repositoryService.activateProcessDefinitionById(id, false, new Date());
        } else {
            // suspendProcessInstances = false，进行中的任务，不进行挂起。
            // 原因：只要新的流程不允许发起即可，老流程继续可以执行。
            repositoryService.suspendProcessDefinitionById(id, false, new Date());
        }
    }

    @Override
    public void updateProcessDefinitionSuspended(String deploymentId) {
        if (StrUtil.isEmpty(deploymentId)) {
            return;
        }

        ProcessDefinition processDefinition = getProcessDefinitionByDeploymentId(deploymentId);
        if (processDefinition == null) {
            return;
        }

        updateProcessDefinitionStatus(processDefinition.getId(), false);
    }

    @Override
    public BpmProcessDefinitionVo getById(String id) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(id).singleResult();

       if (processDefinition == null) {
           return null;
       }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        String bpmnXml = BpmnModelUtils.getBpmnXml(bpmnModel);
        BpmProcessDefinitionVo bpmProcessDefinitionVo = new BpmProcessDefinitionVo();
        bpmProcessDefinitionVo.setBpmnXml(bpmnXml);
        return bpmProcessDefinitionVo;
    }

    @Override
    public PageResult<BpmProcessDefinitionVo> page(BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StrUtil.isNotBlank(bpmProcessDefinitionPageDto.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike(bpmProcessDefinitionPageDto.getKey());
        }
        long count = processDefinitionQuery.count();
        if (count == 0) {
            return PageResult.empty();
        }

        List<ProcessDefinition> processDefinitions = processDefinitionQuery
                .orderByProcessDefinitionVersion().desc()
                .listPage(bpmProcessDefinitionPageDto.getOffset(), bpmProcessDefinitionPageDto.getSize());

        // 获取分类信息
        Map<Long, String> categoryNameMap = bpmCategoryService.getCategoryNameMap(processDefinitions.stream()
                .map(ProcessDefinition::getCategory)
                .map(Long::parseLong)
                .toList());

        // 获取部署信息
        Map<String, Deployment> deploymentMap = getDeploymentMap(processDefinitions.stream().map(ProcessDefinition::getDeploymentId).toList());

        // 获取流程定义信息
        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(processDefinitions.stream().map(ProcessDefinition::getId).toList());

        // 获取表单信息
        Map<Long, String> formNameMap = bpmFormService.getFormNameMap(processDefinitionInfoMap.values().stream().map(BpmProcessDefinitionInfo::getFormId).toList());

        return PageResult.of(count, BpmProcessDefinitionConvert.INSTANCE.convert(processDefinitions, categoryNameMap, processDefinitionInfoMap, formNameMap, deploymentMap));
    }

    @Override
    public List<BpmProcessDefinitionVo> list(BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .active();
        if (StrUtil.isNotBlank(bpmProcessDefinitionPageDto.getCategory())) {
            processDefinitionQuery.processDefinitionCategory(bpmProcessDefinitionPageDto.getCategory());
        }
        long count = processDefinitionQuery.count();
        if (count == 0) {
            return List.of();
        }

        List<ProcessDefinition> processDefinitions = processDefinitionQuery
                .orderByProcessDefinitionCategory().asc().list();

        // 获取分类信息
        Map<Long, String> categoryNameMap = bpmCategoryService.getCategoryNameMap(processDefinitions.stream()
                .map(ProcessDefinition::getCategory)
                .map(Long::parseLong)
                .toList());

        // 获取部署信息
        Map<String, Deployment> deploymentMap = getDeploymentMap(processDefinitions.stream().map(ProcessDefinition::getDeploymentId).toList());

        // 获取流程定义信息
        LambdaQueryWrapper<BpmProcessDefinitionInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(bpmProcessDefinitionPageDto.getVisible() != null, BpmProcessDefinitionInfo::getVisible, bpmProcessDefinitionPageDto.getVisible());
        queryWrapper.in(BpmProcessDefinitionInfo::getProcessDefinitionId, processDefinitions.stream().map(ProcessDefinition::getId).toList());

        List<BpmProcessDefinitionInfo> list = bpmProcessDefinitionInfoService.list(queryWrapper);
        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = list.stream().collect(Collectors.toMap(BpmProcessDefinitionInfo::getProcessDefinitionId, Function.identity()));

        // 获取表单信息
        Map<Long, String> formNameMap = bpmFormService.getFormNameMap(processDefinitionInfoMap.values().stream().map(BpmProcessDefinitionInfo::getFormId).toList());

        return BpmProcessDefinitionConvert.INSTANCE.convert(processDefinitions, categoryNameMap, processDefinitionInfoMap, formNameMap, deploymentMap);
    }
}

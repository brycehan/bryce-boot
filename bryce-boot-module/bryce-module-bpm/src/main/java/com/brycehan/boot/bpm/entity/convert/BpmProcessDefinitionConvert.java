package com.brycehan.boot.bpm.entity.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.entity.po.BpmCategory;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流程定义信息转换器
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BpmProcessDefinitionConvert {

    BpmProcessDefinitionConvert INSTANCE = Mappers.getMapper(BpmProcessDefinitionConvert.class);

    default List<BpmProcessDefinitionVo> convert(List<ProcessDefinition> processDefinitions,
                                                 Map<Long, String> categoryNameMap,
                                                 Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap,
                                                 Map<Long, String> formNameMap,
                                                 Map<String, Deployment> deploymentMap) {
        if (processDefinitions == null || processDefinitions.isEmpty()) {
            return null;
        }
        List<BpmProcessDefinitionVo> bpmProcessDefinitionVos = new ArrayList<>(processDefinitions.size());
        for (ProcessDefinition processDefinition : processDefinitions) {
            BpmProcessDefinitionVo bpmProcessDefinitionVo = new BpmProcessDefinitionVo();
            BpmProcessDefinitionInfo bpmProcessDefinitionInfo = processDefinitionInfoMap.get(processDefinition.getId());
            if (bpmProcessDefinitionInfo == null) {
                continue;
            } else {
                // 表单信息
                bpmProcessDefinitionVo.setFormType(bpmProcessDefinitionInfo.getFormType());
                bpmProcessDefinitionVo.setFormId(bpmProcessDefinitionInfo.getFormId());
                bpmProcessDefinitionVo.setFormName(formNameMap.get(bpmProcessDefinitionInfo.getFormId()));
                bpmProcessDefinitionVo.setFormConf(bpmProcessDefinitionInfo.getFormConf());
                bpmProcessDefinitionVo.setFormFields(bpmProcessDefinitionInfo.getFormFields());
                // 模型信息
                bpmProcessDefinitionVo.setModelType(bpmProcessDefinitionInfo.getModelType());
                bpmProcessDefinitionVo.setDescription(bpmProcessDefinitionInfo.getDescription());
            }

            // 流程定义信息
            bpmProcessDefinitionVo.setId(processDefinition.getId());
            bpmProcessDefinitionVo.setName(processDefinition.getName());
            bpmProcessDefinitionVo.setKey(processDefinition.getKey());
            bpmProcessDefinitionVo.setVersion(processDefinition.getVersion());
            bpmProcessDefinitionVo.setCategory(categoryNameMap.get(Long.parseLong(processDefinition.getCategory())));
            bpmProcessDefinitionVo.setSuspensionState(processDefinition.isSuspended() ? 1 : 0);
            bpmProcessDefinitionVo.setDescription(processDefinition.getDescription());

            // 部署时间
            Deployment deployment = deploymentMap.get(processDefinition.getDeploymentId());
            if (deployment != null) {
                bpmProcessDefinitionVo.setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
            }

            bpmProcessDefinitionVos.add(bpmProcessDefinitionVo);
        }
        return bpmProcessDefinitionVos;
    }

    default BpmProcessDefinitionVo convert(ProcessDefinition processDefinition,
                                                 String categoryName,
                                                 BpmProcessDefinitionInfo bpmProcessDefinitionInfo,
                                                 String formName,
                                                 Deployment deployment) {
        if (processDefinition == null || bpmProcessDefinitionInfo == null) {
            return null;
        }

        BpmProcessDefinitionVo bpmProcessDefinitionVo = new BpmProcessDefinitionVo();

        // 表单信息
        bpmProcessDefinitionVo.setFormType(bpmProcessDefinitionInfo.getFormType());
        bpmProcessDefinitionVo.setFormId(bpmProcessDefinitionInfo.getFormId());
        bpmProcessDefinitionVo.setFormName(formName);
        bpmProcessDefinitionVo.setFormConf(bpmProcessDefinitionInfo.getFormConf());
        bpmProcessDefinitionVo.setFormFields(bpmProcessDefinitionInfo.getFormFields());

        // 模型信息
        bpmProcessDefinitionVo.setModelType(bpmProcessDefinitionInfo.getModelType());
        bpmProcessDefinitionVo.setDescription(bpmProcessDefinitionInfo.getDescription());

        // 流程定义信息
        bpmProcessDefinitionVo.setId(processDefinition.getId());
        bpmProcessDefinitionVo.setName(processDefinition.getName());
        bpmProcessDefinitionVo.setKey(processDefinition.getKey());
        bpmProcessDefinitionVo.setVersion(processDefinition.getVersion());
        bpmProcessDefinitionVo.setCategoryName(categoryName);
        bpmProcessDefinitionVo.setSuspensionState(processDefinition.isSuspended() ? 1 : 0);
        bpmProcessDefinitionVo.setDescription(processDefinition.getDescription());

        // 部署时间
        if (deployment != null) {
            bpmProcessDefinitionVo.setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
        }

        return bpmProcessDefinitionVo;
    }

    default BpmProcessDefinitionVo buildProcessDefinition(ProcessDefinition definition,
                                                              Deployment deployment,
                                                              BpmProcessDefinitionInfo processDefinitionInfo,
                                                              BpmForm form,
                                                              BpmCategory category,
                                                              BpmnModel bpmnModel) {
        BpmProcessDefinitionVo respVO = BeanUtil.toBean(definition, BpmProcessDefinitionVo.class);
        respVO.setSuspensionState(definition.isSuspended() ? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode());
        // Deployment
        if (deployment != null) {
            respVO.setDeploymentTime(LocalDateTimeUtil.of(deployment.getDeploymentTime()));
        }
        // BpmProcessDefinitionInfoDO
        if (processDefinitionInfo != null) {
            copyTo(processDefinitionInfo, respVO);
            // Form
            if (form != null) {
                respVO.setFormName(form.getName());
            }
        }
        // Category
        if (category != null) {
            respVO.setCategoryName(category.getName());
        }
        // BpmnModel
        if (bpmnModel != null) {
            respVO.setBpmnXml(BpmnModelUtils.getBpmnXml(bpmnModel));
        }
        return respVO;
    }

    @Mapping(target = "to.id", source = "from.id", ignore = true)
    void copyTo(BpmProcessDefinitionInfo from, @MappingTarget BpmProcessDefinitionVo to);
}
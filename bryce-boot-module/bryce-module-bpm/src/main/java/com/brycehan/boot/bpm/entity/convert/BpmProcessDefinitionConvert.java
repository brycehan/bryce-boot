package com.brycehan.boot.bpm.entity.convert;

import cn.hutool.core.date.DateUtil;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
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
            // 流程定义信息
            bpmProcessDefinitionVo.setId(processDefinition.getId());
            bpmProcessDefinitionVo.setName(processDefinition.getName());
            bpmProcessDefinitionVo.setKey(processDefinition.getKey());
            bpmProcessDefinitionVo.setVersion(processDefinition.getVersion());
            bpmProcessDefinitionVo.setCategory(categoryNameMap.get(Long.parseLong(processDefinition.getCategory())));
            bpmProcessDefinitionVo.setSuspensionState(processDefinition.isSuspended() ? 1 : 0);
            bpmProcessDefinitionVo.setDescription(processDefinition.getDescription());

            BpmProcessDefinitionInfo bpmProcessDefinitionInfo = processDefinitionInfoMap.get(processDefinition.getId());
            if (bpmProcessDefinitionInfo != null) {
                // 表单信息
                bpmProcessDefinitionVo.setFormType(bpmProcessDefinitionInfo.getFormType());
                bpmProcessDefinitionVo.setFormId(bpmProcessDefinitionInfo.getFormId());
                bpmProcessDefinitionVo.setFormName(formNameMap.get(bpmProcessDefinitionInfo.getFormId()));
                bpmProcessDefinitionVo.setFormConf(bpmProcessDefinitionInfo.getFormConf());
                bpmProcessDefinitionVo.setFormFields(bpmProcessDefinitionInfo.getFormFields());
                // 模型信息
                bpmProcessDefinitionVo.setDescription(bpmProcessDefinitionInfo.getDescription());
            }

            // 部署时间
            Deployment deployment = deploymentMap.get(processDefinition.getDeploymentId());
            if (deployment != null) {
                bpmProcessDefinitionVo.setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
            }

            bpmProcessDefinitionVos.add(bpmProcessDefinitionVo);
        }
        return bpmProcessDefinitionVos;
    }

}
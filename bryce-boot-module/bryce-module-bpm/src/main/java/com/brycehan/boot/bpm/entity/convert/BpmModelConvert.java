package com.brycehan.boot.bpm.entity.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelMetaInfoDto;
import com.brycehan.boot.bpm.entity.vo.BpmModelMetaInfoVo;
import com.brycehan.boot.bpm.entity.vo.BpmModelVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import com.brycehan.boot.common.util.JsonUtils;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 流程定义信息转换器
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Mapper
public interface BpmModelConvert {

    BpmModelConvert INSTANCE = Mappers.getMapper(BpmModelConvert.class);

    default void copyToModel(Model model, BpmModelDto bpmModelDto){
        model.setName(bpmModelDto.getName());
        model.setKey(bpmModelDto.getKey());
        model.setCategory(bpmModelDto.getCategory());
        model.setMetaInfo(JsonUtils.writeValueAsString(BeanUtil.toBean(bpmModelDto, BpmModelMetaInfoDto.class)));
    }

    /**
     * 解析流程模型元数据
     *
     * @param model 流程定义
     * @return BpmModelMetaInfoDto
     */
    @SuppressWarnings("all")
    default BpmModelMetaInfoDto parseMetaInfoDto(Model model) {
        BpmModelMetaInfoDto bpmModelMetaInfoDto = JsonUtils.readValue(model.getMetaInfo(), BpmModelMetaInfoDto.class);
        if (bpmModelMetaInfoDto == null) {
            return null;
        }
        if (bpmModelMetaInfoDto.getManagerUserIds() == null) {
            bpmModelMetaInfoDto.setManagerUserIds(Collections.emptyList());
        }
        if (bpmModelMetaInfoDto.getStartUserIds() == null) {
            bpmModelMetaInfoDto.setStartUserIds(Collections.emptyList());
        }
        if (bpmModelMetaInfoDto.getSort() == null) {
            bpmModelMetaInfoDto.setSort(model.getCreateTime().getTime());
        }
        return bpmModelMetaInfoDto;
    }

    @SuppressWarnings("all")
    default BpmModelMetaInfoVo parseMetaInfoVo(Model model) {
        BpmModelMetaInfoVo bpmModelMetaInfoVo = JsonUtils.readValue(model.getMetaInfo(), BpmModelMetaInfoVo.class);
        if (bpmModelMetaInfoVo == null) {
            return null;
        }
        if (bpmModelMetaInfoVo.getManagerUserIds() == null) {
            bpmModelMetaInfoVo.setManagerUserIds(Collections.emptyList());
        }
        if (bpmModelMetaInfoVo.getStartUserIds() == null) {
            bpmModelMetaInfoVo.setStartUserIds(Collections.emptyList());
        }
        if (bpmModelMetaInfoVo.getSort() == null) {
            bpmModelMetaInfoVo.setSort(model.getCreateTime().getTime());
        }
        return bpmModelMetaInfoVo;
    }

    /**
     * 转换
     *
     * @param models              流程模型列表
     * @param formNameMap         表单名称
     * @param categoryNameMap     分类名称
     * @param deploymentMap       部署
     * @param processDefinitionMap 流程定义
     * @return BpmModelVo
     */
    default List<BpmModelVo> convert(List<Model> models, Map<Long, String> formNameMap, Map<Long, String> categoryNameMap,
                                     Map<String, Deployment> deploymentMap, Map<String, ProcessDefinition> processDefinitionMap){
        return models.stream().map(model -> {
            BpmModelVo bpmModelVo = new BpmModelVo();
            bpmModelVo.setId(model.getId());
            bpmModelVo.setName(model.getName());
            bpmModelVo.setKey(model.getKey());
            bpmModelVo.setCategory(model.getCategory());
            bpmModelVo.setCreateTime(DateUtil.toLocalDateTime(model.getCreateTime()));

            BpmModelMetaInfoVo metaInfo = parseMetaInfoVo(model);

            // 表单
            BeanUtil.copyProperties(metaInfo, bpmModelVo);
            if (metaInfo != null) {
                bpmModelVo.setFormName(formNameMap.get(metaInfo.getFormId()));
            }

            // 分类
            bpmModelVo.setCategoryName(categoryNameMap.get(Long.parseLong(model.getCategory())));

            // 流程定义
            if (processDefinitionMap.containsKey(model.getDeploymentId())) {
                ProcessDefinition processDefinition = processDefinitionMap.get(model.getDeploymentId());
                bpmModelVo.setProcessDefinition(BeanUtil.toBean(processDefinition, BpmProcessDefinitionVo.class));
                bpmModelVo.getProcessDefinition().setSuspensionState(processDefinition.isSuspended() ? 1 : 0);

                // 部署
                Deployment deployment = deploymentMap.get(model.getDeploymentId());
                if (deployment != null) {
                    bpmModelVo.getProcessDefinition().setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
                }
            }

            return bpmModelVo;
        }).toList();
    }

    default BpmModelVo convert(Model model, byte[] bpmnXml, BpmSimpleModelNodeVo simpleModel) {
        BpmModelVo bpmModelVo = new BpmModelVo();

        BpmModelMetaInfoVo metaInfo = parseMetaInfoVo(model);
        BeanUtil.copyProperties(metaInfo, bpmModelVo);

        bpmModelVo.setId(model.getId());
        bpmModelVo.setName(model.getName());
        bpmModelVo.setKey(model.getKey());
        bpmModelVo.setCategory(model.getCategory());
        bpmModelVo.setCreateTime(DateUtil.toLocalDateTime(model.getCreateTime()));

        bpmModelVo.setBpmnXml(StrUtil.utf8Str(bpmnXml));
        bpmModelVo.setSimpleModel(simpleModel);

        return bpmModelVo;
    }
}
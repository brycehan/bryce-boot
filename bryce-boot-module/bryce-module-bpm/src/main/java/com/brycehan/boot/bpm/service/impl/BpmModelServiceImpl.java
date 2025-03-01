package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.common.BpmModelType;
import com.brycehan.boot.bpm.entity.convert.BpmModelConvert;
import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelMetaInfoDto;
import com.brycehan.boot.bpm.service.BpmFormService;
import com.brycehan.boot.bpm.service.BpmModelService;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Model;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/**
 * 流程定义信息服务实现
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmModelServiceImpl implements BpmModelService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final HistoryService historyService;
    private final BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;
    private final BpmFormService bpmFormService;

    /**
     * 添加模型
     *
     * @param bpmModelDto BPM 模型 Dto
     */
    @Override
    @Transactional
    public void save(BpmModelDto bpmModelDto) {
        // 校验流程标识是否已经存在
        Model model = repositoryService.createModelQuery()
                .modelKey(bpmModelDto.getKey())
                .singleResult();
        if (model != null) {
            throw ServerException.of(BpmResponseStatus.BPM_MODEL_KEY_EXISTS, bpmModelDto.getKey());
        }

        // 创建 Model 对象
        model = repositoryService.newModel();
        BpmModelConvert.INSTANCE.copyToModel(model, bpmModelDto);

        // 保存 Model 对象
        ((BpmModelServiceImpl) AopContext.currentProxy()).saveModel(model, bpmModelDto);
    }

    /**
     * 更新模型
     *
     * @param bpmModelDto BPM 模型 Dto
     */
    @Override
    @Transactional
    public void update(BpmModelDto bpmModelDto) {
        // 校验操作流程模型的权限
        Model model = validateModelManager(bpmModelDto.getId(), LoginUserContext.currentUserId());
        // 填充 Model 信息
        BpmModelConvert.INSTANCE.copyToModel(model, bpmModelDto);
        // 保存 Model 对象
        ((BpmModelServiceImpl) AopContext.currentProxy()).saveModel(model, bpmModelDto);
    }

    /**
     * 保存模型
     *
     * @param model       Model
     * @param bpmModelDto BPM 模型 Dto
     */
    @Transactional
    protected void saveModel(Model model, BpmModelDto bpmModelDto) {
        // 保存 Model 对象
        repositoryService.saveModel(model);
        // 保存流程定义信息
        if (Objects.equals(BpmModelType.BPMN.getValue(), bpmModelDto.getType())) {
            updateModelBpmnXml(model.getId(), bpmModelDto.getBpmnXml());
        } else {
            if (bpmModelDto.getSimpleModel() == null) {
                return;
            }
        }
    }

    @Override
    public void updateModelBpmnXml(String id, String bpmnXml) {
        if (StrUtil.isBlank(bpmnXml)) {
            return;
        }

        repositoryService.addModelEditorSource(id, StrUtil.utf8Bytes(bpmnXml));
    }

    /**
     * 校验用户是否是模型的管理员
     *
     * @param modelId 模型ID
     * @param userId  用户ID
     * @return Model
     */
    private Model validateModelManager(String modelId, Long userId) {
        Model model = validateModelExist(modelId);
        BpmModelMetaInfoDto metaInfo = BpmModelConvert.INSTANCE.parseMetaInfo(model);
        if (metaInfo == null || !CollUtil.contains(metaInfo.getManagerUserIds(), userId)) {
            throw ServerException.of(BpmResponseStatus.BPM_MODEL_UPDATE_NOT_MANAGER, modelId);
        }
        return model;
    }

    /**
     * 校验模型是否存在
     *
     * @param modelId 模型ID
     * @return Model
     */
    private Model validateModelExist(String modelId) {
        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            throw ServerException.of(BpmResponseStatus.BPM_MODEL_NOT_EXISTS, modelId);
        }
        return model;
    }
}

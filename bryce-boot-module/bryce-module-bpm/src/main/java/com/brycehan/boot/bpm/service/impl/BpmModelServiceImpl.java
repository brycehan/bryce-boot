package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.common.BpmFormType;
import com.brycehan.boot.bpm.common.BpmModelType;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateInvoker;
import com.brycehan.boot.bpm.common.type.BpmReasonEnum;
import com.brycehan.boot.bpm.entity.convert.BpmModelConvert;
import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelKeyDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelMetaInfoDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelPageDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.vo.BpmModelMetaInfoVo;
import com.brycehan.boot.bpm.entity.vo.BpmModelVo;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import com.brycehan.boot.bpm.service.*;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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
    private final BpmProcessDefinitionService bpmProcessDefinitionService;
    private final BpmFormService bpmFormService;
    private final BpmCategoryService bpmCategoryService;
    private final BpmTaskCandidateInvoker taskCandidateInvoker;
    private final BpmProcessInstanceCopyService bpmProcessInstanceCopyService;
    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    /**
     * 添加模型
     *
     * @param bpmModelDto BPM 模型 Dto
     */
    @Override
    @Transactional
    public String save(BpmModelDto bpmModelDto) {
        // 1. 校验流程标识是否已经存在
        Model model = repositoryService.createModelQuery()
                .modelTenantId(FlowableUtils.getTenantId())
                .modelKey(bpmModelDto.getKey())
                .singleResult();
        if (model != null) {
            throw ServerException.of(BpmResponseStatus.MODEL_KEY_EXISTS, bpmModelDto.getKey());
        }

        // 2. 创建 Model 对象
        model = repositoryService.newModel();
        BpmModelConvert.INSTANCE.copyToModel(model, bpmModelDto);
        model.setTenantId(FlowableUtils.getTenantId());

        // 3. 保存 Model 对象
        ((BpmModelServiceImpl) AopContext.currentProxy()).saveModel(model, bpmModelDto);
        return model.getId();
    }

    /**
     * 更新模型
     *
     * @param bpmModelDto BPM 模型 Dto
     */
    @Override
    @Transactional
    public void update(BpmModelDto bpmModelDto) {
        // 1. 校验操作流程模型的权限
        Model model = validateModelExist(bpmModelDto.getId());
        validateModelManager(model, LoginUserContext.currentUserId());
        // 2. 填充模型信息
        BpmModelConvert.INSTANCE.copyToModel(model, bpmModelDto);
        // 3. 保存模型
        ((BpmModelServiceImpl) AopContext.currentProxy()).saveModel(model, bpmModelDto);
    }

    @Override
    @Transactional
    public void delete(List<String> ids, Long userId) {
        for (String id : ids) {
            // 1. 校验流程模型是否存在
            Model model = validateModelExist(id, false);
            if (model == null) {
                continue;
            }
            // 2. 校验操作流程模型的权限
            validateModelManager(model, userId);

            // 3. 删除模型
            repositoryService.deleteModel(id);
            // 4. 禁用流程定义
            bpmProcessDefinitionService.updateProcessDefinitionSuspended(model.getDeploymentId());
        }
    }

    @Override
    public BpmModelVo getById(String id) {
        Model model = repositoryService.getModel(id);
        if (model == null) {
            return null;
        }

        byte[] bpmnXml = getModelBpmnXml(id);
        BpmSimpleModelNodeVo simpleModel = getSimpleModel(id);
        return BpmModelConvert.INSTANCE.convert(model, bpmnXml, simpleModel);
    }

    @Override
    public PageResult<BpmModelVo> page(BpmModelPageDto bpmModelPageDto) {
        ModelQuery modelQuery = repositoryService.createModelQuery();

        if (StrUtil.isNotBlank(bpmModelPageDto.getName())) {
            modelQuery.modelNameLike("%" + bpmModelPageDto.getName() + "%");
        }

        // 分页查询模型列表
        List<Model> models = modelQuery.listPage(bpmModelPageDto.getOffset(), bpmModelPageDto.getSize());

        if (CollUtil.isEmpty(models)) {
            return PageResult.empty();
        }

        // 查询表单名称信息
        List<Long> formIds = models.stream().map(BpmModelConvert.INSTANCE::parseMetaInfoVo).map(BpmModelMetaInfoVo::getFormId).toList();
        Map<Long, String> formNameMap = bpmFormService.getFormNameMap(formIds);

        // 查询流程分类
        List<Long> categoryIds = models.stream().map(Model::getCategory).map(Long::parseLong).toList();
        Map<Long, String> categoryNameMap = bpmCategoryService.getCategoryNameMap(categoryIds);

        // 查询最新部署信息
        List<String> deploymentIds = models.stream().map(Model::getDeploymentId).toList();
        Map<String, Deployment> deploymentMap = bpmProcessDefinitionService.getDeploymentMap(deploymentIds);

        // 查询最新部署的流程定义
        List<ProcessDefinition> processDefinitions = bpmProcessDefinitionService.getProcessDefinitionsByDeploymentIds(deploymentIds);
        Map<String, ProcessDefinition> processDefinitionMap = processDefinitions.stream().collect(Collectors.toMap(ProcessDefinition::getDeploymentId, pd -> pd));

        List<BpmModelVo> list = BpmModelConvert.INSTANCE.convert(models, formNameMap, categoryNameMap, deploymentMap, processDefinitionMap);
        return PageResult.of(list, modelQuery.count());
    }

    /**
     * 保存模型
     *
     * @param model       Model
     * @param bpmModelDto BPM 模型 Dto
     */
    @Transactional
    protected void saveModel(Model model, BpmModelDto bpmModelDto) {
        // 1. 保存 Model 对象
        repositoryService.saveModel(model);

        // 2. 保存流程图信息
        if (Objects.equals(BpmModelType.BPMN.getValue(), bpmModelDto.getType())) {
            ((BpmModelServiceImpl) AopContext.currentProxy()).updateModelBpmnXml(model.getId(), bpmModelDto.getBpmnXml());
        }
    }

    @Transactional
    @Override
    public void updateModelBpmnXml(String id, String bpmnXml) {
        if (StrUtil.isBlank(bpmnXml)) {
            return;
        }
        // 校验流程图
        validateBpmnXml(bpmnXml.getBytes(StandardCharsets.UTF_8));

        repositoryService.addModelEditorSource(id, StrUtil.utf8Bytes(bpmnXml));
    }

    @Override
    public byte[] getModelBpmnXml(String id) {
        return repositoryService.getModelEditorSource(id);
    }

    @Override
    public BpmSimpleModelNodeVo getSimpleModel(String id) {
        Model model = repositoryService.getModel(id);
        if (model == null) {
            return null;
        }

        // 通过 ACT_RE_MODEL 表 EDITOR_SOURCE_EXTRA_VALUE_ID_ ，获取仿钉钉快搭模型的 JSON 数据
        String simpleModelJson = getSimpleModelJson(id);
        return JsonUtils.readValue(simpleModelJson, BpmSimpleModelNodeVo.class);
    }

    @Transactional
    @Override
    public void deploy(String id, Long userId) {
        // 1.1. 校验模型是否存在
        Model model = validateModelExist(id);
        validateModelManager(model, userId);

        // 1.2. 校验表单已经配置
        BpmModelMetaInfoVo metaInfo = BpmModelConvert.INSTANCE.parseMetaInfoVo(model);
        BpmForm bpmForm = validateFormConfig(metaInfo);

        // 1.3. 校验流程图
        byte[] bpmnXml = getModelBpmnXml(id);
        validateBpmnXml(bpmnXml);

        // 1.4. 校验任务分配规则是否配置
        taskCandidateInvoker.validateBpmnConfig(bpmnXml);

        // 2.1. 部署流程
        ProcessDefinition processDefinition = bpmProcessDefinitionService.deploy(model, metaInfo, bpmnXml, null, bpmForm);

        // 2.2. 将旧的流程定义进行挂起。也就是说，只有最新部署的流程定义，才可以发起任务
        bpmProcessDefinitionService.updateProcessDefinitionSuspended(model.getDeploymentId());

        // 2.3. 更新模型的 deploymentId, 进行关联
        model.setDeploymentId(processDefinition.getDeploymentId());
        repositoryService.saveModel(model);
    }

    @Override
    public void updateState(String id, Integer state, Long userId) {
        // 1. 校验模型是否存在
        Model model = validateModelExist(id);
        validateModelManager(model, userId);
        // 2. 校验流程定义是否存在
        ProcessDefinition processDefinition = bpmProcessDefinitionService.getProcessDefinitionByDeploymentId(model.getDeploymentId());
        if (processDefinition == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_NOT_EXIST, model.getDeploymentId());
        }
        // 3. 更新流程定义状态
        bpmProcessDefinitionService.updateProcessDefinitionStatus(processDefinition.getId(), state == 1);
    }

    @Override
    public void cleanModel(String id, Long userId) {
        // 1. 校验流程模型存在与权限
        Model model = validateModelExist(id);
        validateModelManager(model, userId);

        // 2. 清理所有流程数据
        // 2.1 先取消所有正在运行的流程
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey(model.getKey()).list();
        processInstances.forEach(processInstance -> {
            runtimeService.deleteProcessInstance(processInstance.getId(), BpmReasonEnum.CANCEL_BY_SYSTEM.getReason());
            historyService.deleteHistoricProcessInstance(processInstance.getId());
            bpmProcessInstanceCopyService.deleteProcessInstanceCopy(processInstance.getId());
        });
        // 2.2 再从历史中删除所有相关的流程数据
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(model.getKey()).list();
        historicProcessInstances.forEach(historicProcessInstance -> {
            historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
            bpmProcessInstanceCopyService.deleteProcessInstanceCopy(historicProcessInstance.getId());
        });
        // 2.3 清理所有 Task
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(model.getKey()).list();
        tasks.forEach(task -> taskService.deleteTask(task.getId(),BpmReasonEnum.CANCEL_BY_PROCESS_CLEAN.getReason()));
    }

    @Override
    public boolean checkKeyUnique(BpmModelKeyDto bpmModelKeyDto) {

        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.modelKey(bpmModelKeyDto.getKey());
        // 查询模型
        Model model = modelQuery.singleResult();

        // 修改时，同Key同ID为Key唯一
        return Objects.isNull(model) || Objects.equals(bpmModelKeyDto.getId(), model.getId());
    }

    /**
     * 校验表单是否已经配置
     *
     * @param metaInfo 模型元信息
     * @return BpmForm
     */
    private BpmForm validateFormConfig(BpmModelMetaInfoVo metaInfo) {
        if (metaInfo == null || metaInfo.getFormType() == null) {
            throw ServerException.of(BpmResponseStatus.MODEL_FORM_NOT_CONFIG);
        }

        // 校验表单是否存在
        if (BpmFormType.NORMAL.getValue().equals(metaInfo.getFormType())) {
            if (metaInfo.getFormId() == null) {
                throw ServerException.of(BpmResponseStatus.MODEL_FORM_NOT_CONFIG);
            }
            BpmForm bpmForm = bpmFormService.getById(metaInfo.getFormId());
            if (bpmForm == null) {
                throw ServerException.of(BpmResponseStatus.MODEL_FORM_NOT_EXIST, metaInfo.getFormId());
            }
            return bpmForm;
        } else {
            if (StrUtil.isEmpty(metaInfo.getFormCustomCreatePath()) || StrUtil.isEmpty(metaInfo.getFormCustomViewPath())) {
                throw ServerException.of(BpmResponseStatus.MODEL_FORM_NOT_CONFIG);
            }
            return null;
        }
    }

    /**
     * 获取简单模型JSON
     *
     * @param id 模型ID
     * @return 简单模型JSON
     */
    private String getSimpleModelJson(String id) {
        byte[] modelEditorSourceExtra = repositoryService.getModelEditorSourceExtra(id);
        return StrUtil.utf8Str(modelEditorSourceExtra);
    }

    /**
     * 校验用户是否是模型的管理员
     *
     * @param model 模型
     * @param userId  用户ID
     */
    private void validateModelManager(Model model, Long userId) {
        BpmModelMetaInfoDto metaInfo = BpmModelConvert.INSTANCE.parseMetaInfoDto(model);
        if (metaInfo == null || !(LoginUser.isSuperAdmin(userId) || CollUtil.contains(metaInfo.getManagerUserIds(), userId))) {
            throw ServerException.of(BpmResponseStatus.MODEL_UPDATE_NOT_MANAGER, model.getId());
        }
    }

    /**
     * 校验模型是否存在
     *
     * @param modelId 模型ID
     * @return Model
     */
    private Model validateModelExist(String modelId, boolean throwException) {
        Model model = repositoryService.getModel(modelId);
        if (model == null && throwException) {
            throw ServerException.of(BpmResponseStatus.MODEL_NOT_EXISTS, modelId);
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
        return validateModelExist(modelId, true);
    }

    private void validateBpmnXml(byte[] bpmnXml) {
        // 1. 获取 BpmnModel
        BpmnModel bpmnModel = BpmnModelUtils.getBpmnModel(bpmnXml);
        if (bpmnModel == null) {
            throw ServerException.of(BpmResponseStatus.MODEL_NOT_EXISTS);
        }

        // 2. 没有 StartEvent
        StartEvent startEvent = BpmnModelUtils.getStartEvent(bpmnModel);
        if (startEvent == null) {
            throw ServerException.of(BpmResponseStatus.MODEL_BPMN_START_EVENT_NOT_EXISTS);
        }

        // 3. 校验 UserTask 的 name 属性是否配置
        List<UserTask> userTasks = BpmnModelUtils.getFlowElementsOfType(bpmnModel, UserTask.class);
        userTasks.forEach(userTask -> {
            if (StrUtil.isBlank(userTask.getName())) {
                throw ServerException.of(BpmResponseStatus.MODEL_BPMN_USER_TASK_NAME_NOT_EXISTS, userTask.getId());
            }
        });
    }
}

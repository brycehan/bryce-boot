package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.BpmDeptApi;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.common.*;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateInvoker;
import com.brycehan.boot.bpm.common.candidate.strategy.dept.BpmTaskCandidateStartUserSelectStrategy;
import com.brycehan.boot.bpm.common.type.BpmProcessInstanceStatusEnum;
import com.brycehan.boot.bpm.common.type.BpmReasonEnum;
import com.brycehan.boot.bpm.common.type.BpmSimpleModelNodeTypeEnum;
import com.brycehan.boot.bpm.common.type.BpmTaskStatusEnum;
import com.brycehan.boot.bpm.entity.convert.BpmProcessInstanceConvert;
import com.brycehan.boot.bpm.entity.dto.BpmApprovalDetailDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCancelDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstancePageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.*;
import com.brycehan.boot.bpm.service.*;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.*;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.flowable.bpmn.constants.BpmnXMLConstants.*;


/**
 * 流程实例服务实现
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmProcessInstanceServiceImpl implements BpmProcessInstanceService {

    private final BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;
    private final BpmProcessDefinitionService bpmProcessDefinitionService;
    private final BpmProcessIdRedisDao bpmProcessIdRedisDao;
    private final RepositoryService repositoryService;
    private final BpmMessageService bpmMessageService;
    private final BpmProcessInstanceEventPublisher processInstanceEventPublisher;
    private final BpmTaskCandidateInvoker taskCandidateInvoker;
    private final BpmCategoryService bpmCategoryService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    @Resource
    @Lazy
    private final BpmTaskService bpmTaskService;

    @Resource
    @Lazy
    private BpmUserApi bpmUserApi;

    @Resource
    @Lazy
    private BpmDeptApi bpmDeptApi;

    @Transactional
    @Override
    public String createProcessInstance(Long userId, BpmProcessInstanceDto bpmProcessInstanceDto) {
        // 获得流程定义
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(bpmProcessInstanceDto.getProcessDefinitionId());

        // 校验流程定义
        if (processDefinition == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_NOT_EXIST);
        }
        if (processDefinition.isSuspended()) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_IS_SUSPENDED);
        }
        BpmProcessDefinitionInfo processDefinitionInfo = bpmProcessDefinitionInfoService.getProcessDefinitionInfo(processDefinition.getId());
        if (processDefinitionInfo == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_NOT_EXIST);
        }

        // 校验是否可以发起

        // 校验发起人自选审批人
        validateStartUserSelectAssignees(processDefinition, bpmProcessInstanceDto.getStartUserSelectAssignees());

        // 创建流程实例
        Map<String, Object> variables = bpmProcessInstanceDto.getVariables();

        if (variables == null) {
            variables = new HashMap<>();
        }
        FlowableUtils.filterProcessInstanceFormVariable(variables); // 过滤一下，避免 ProcessInstance 系统级的变量被占用
        variables.put(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_START_USER_ID, userId); // 设置流程变量，发起人 ID
        // 流程实例状态：审批中
        variables.put(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS, BpmProcessInstanceStatusEnum.RUNNING.getValue());
        // 跳过表达式需要添加此变量为true，不影响没配置skipExpression 的节点
        variables.put(BpmnVariableConstants.PROCESS_INSTANCE_SKIP_EXPRESSION_ENABLED, true);

        Map<String, List<Long>> startUserSelectAssignees = bpmProcessInstanceDto.getStartUserSelectAssignees();
        if (CollUtil.isNotEmpty(startUserSelectAssignees)) {
            variables.put(BpmnVariableConstants.PROCESS_START_USER_SELECT_ASSIGNEES, startUserSelectAssignees);
        }

        // 3. 创建流程
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder()
                .processDefinitionId(processDefinition.getId())
                .businessKey(bpmProcessInstanceDto.getBusinessKey())
                .variables(variables);
        // 3.1 创建流程 ID
        BpmModelMetaInfoVo.ProcessIdRule processIdRule = processDefinitionInfo.getProcessIdRule();
        if (processIdRule != null && Boolean.TRUE.equals(processIdRule.getEnable())) {
            processInstanceBuilder.predefineProcessInstanceId(bpmProcessIdRedisDao.generate(processIdRule));
        }
        // 3.2 流程名称
        BpmModelMetaInfoVo.TitleSetting titleSetting = processDefinitionInfo.getTitleSetting();
        if (titleSetting != null && Boolean.TRUE.equals(titleSetting.getEnable())) {
            BpmUserVo user = bpmUserApi.getUser(userId);
            Map<String, Object> cloneVariables = new HashMap<>(variables);
            cloneVariables.put(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_START_USER_ID, user.getNickname());
            cloneVariables.put(BpmnVariableConstants.PROCESS_START_TIME, DateUtil.now());
            cloneVariables.put(BpmnVariableConstants.PROCESS_DEFINITION_NAME, processDefinition.getName().trim());
            processInstanceBuilder.name(StrUtil.format(titleSetting.getTitle(), cloneVariables));
        } else {
            processInstanceBuilder.name(processDefinition.getName().trim());
        }
        // 3.3 发起流程实例
        ProcessInstance instance = processInstanceBuilder.start();
        return instance.getId();
    }

    private void validateStartUserSelectAssignees(ProcessDefinition processDefinition, Map<String, List<Long>> startUserSelectAssignees) {
        // 1. 获得发起人自选审批人的 UserTask/ServiceTask 列表
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        List<org.flowable.bpmn.model.Task> tasks = BpmTaskCandidateStartUserSelectStrategy.getStartUserSelectTaskList(bpmnModel);
        if (CollUtil.isEmpty(tasks)) {
            return;
        }

        // 2. 校验发起人自选审批人的审批人和抄送人是否都配置了
        tasks.forEach(task -> {
            List<Long> assignees = startUserSelectAssignees != null ? startUserSelectAssignees.get(task.getId()) : null;
            if (CollUtil.isEmpty(assignees)) {
                throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_CONFIG, task.getName());
            }
            Map<Long, BpmUserVo> userMap = bpmUserApi.getUserMap(assignees);
            assignees.forEach(assignee -> {
                if (userMap.get(assignee) == null) {
                    throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_EXISTS, task.getName(), assignee);
                }
            });
        });
    }

    @Override
    public ProcessInstance getProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

    @Override
    public List<ProcessInstance> getProcessInstances(List<String> processInstanceIds) {
        return runtimeService.createProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceIds(Set.copyOf(processInstanceIds))
                .list();
    }

    @Override
    public HistoricProcessInstance getHistoricProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .includeProcessVariables()
                .singleResult();
    }

    @Override
    public BpmProcessInstanceVo getById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance(processInstanceId);
        if (historicProcessInstance == null) {
            return null;
        }

        // 拼接返回
        ProcessDefinition processDefinition = bpmProcessDefinitionService.getProcessDefinition(processInstanceId);
        BpmProcessDefinitionInfo processDefinitionInfo = bpmProcessDefinitionInfoService.getProcessDefinitionInfo(historicProcessInstance.getProcessDefinitionId());
        BpmUserVo startUser = bpmUserApi.getUser(NumberUtil.parseLong(historicProcessInstance.getStartUserId(), null));
        BpmDeptVo dept = null;
        if (startUser != null && startUser.getDeptId() != null) {
            dept = bpmDeptApi.getDept(startUser.getDeptId());
        }
        return BpmProcessInstanceConvert.INSTANCE.buildProcessInstance(historicProcessInstance, processDefinition, processDefinitionInfo, startUser, dept);
    }

    @Override
    public List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> processInstanceIds) {
        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceIds(Set.copyOf(processInstanceIds))
                .includeProcessVariables()
                .list();
    }

    @Override
    public PageResult<HistoricProcessInstance> getProcessInstancePage(Long userId, BpmProcessInstancePageDto pageDto) {
        // 1. 构建查询条件
        HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceTenantId(FlowableUtils.getTenantId())
                .orderByProcessInstanceStartTime().desc();
        if (userId != null) { // 【我的流程】菜单时，需要传递该字段
            processInstanceQuery.startedBy(String.valueOf(userId));
        } else if (pageDto.getStartUserId() != null) { // 【管理流程】菜单时，才会传递该字段
            processInstanceQuery.startedBy(String.valueOf(pageDto.getStartUserId()));
        }
        if (ObjectUtil.isNotEmpty(pageDto.getId())) {
            processInstanceQuery.processInstanceId(pageDto.getId());
        }
        if (StrUtil.isNotEmpty(pageDto.getName())) {
            processInstanceQuery.processInstanceNameLike("%" + pageDto.getName() + "%");
        }
        if (StrUtil.isNotEmpty(pageDto.getProcessDefinitionKey())) {
            processInstanceQuery.processDefinitionKey(pageDto.getProcessDefinitionKey());
        }
        if (StrUtil.isNotEmpty(pageDto.getCategory())) {
            processInstanceQuery.processDefinitionCategory(pageDto.getCategory());
        }
        if (pageDto.getStatus() != null) {
            processInstanceQuery.variableValueEquals(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS,
                    pageDto.getStatus());
        }
        if (pageDto.getCreateTimeStart() != null && pageDto.getCreateTimeEnd() != null) {
            processInstanceQuery.startedAfter(new DateTime(pageDto.getCreateTimeStart()));
            processInstanceQuery.startedBefore(new DateTime(pageDto.getCreateTimeEnd()));
        }
        if (pageDto.getEndTimeStart() != null && pageDto.getEndTimeEnd() != null) {
            processInstanceQuery.finishedAfter(new DateTime(pageDto.getEndTimeStart()));
            processInstanceQuery.finishedBefore(new DateTime(pageDto.getEndTimeEnd()));
        }
        // 表单字段查询
        Map<String, Object> formFieldsParams = JsonUtils.readValue(pageDto.getFormFieldsParams(), Map.class);
        if (CollUtil.isNotEmpty(formFieldsParams)) {
            formFieldsParams.forEach((key, value) -> {
                if (StrUtil.isEmpty(String.valueOf(value))) {
                    return;
                }
                // 应支持多种类型的查询方式，目前只有字符串全等
                processInstanceQuery.variableValueEquals(key, value);
            });
        }

        // 2.1 查询数量
        long processInstanceCount = processInstanceQuery.count();
        if (processInstanceCount == 0) {
            return PageResult.empty();
        }
        // 2.2 查询列表
        List<HistoricProcessInstance> processInstanceList = processInstanceQuery.listPage(pageDto.getOffset(), pageDto.getSize());
        return new PageResult<>(processInstanceCount, processInstanceList);
    }

    private Map<String, String> getFormFieldsPermission(BpmnModel bpmnModel,
                                                        String activityId, String taskId) {
        // 1. 获取流程活动编号。流程活动 Id 为空事，从流程任务中获取流程活动 Id
        if (StrUtil.isEmpty(activityId) && StrUtil.isNotEmpty(taskId)) {
            activityId = Optional.ofNullable(bpmTaskService.getHistoricTask(taskId))
                    .map(HistoricTaskInstance::getTaskDefinitionKey).orElse(null);
        }
        if (StrUtil.isEmpty(activityId)) {
            return null;
        }

        // 2. 从 BpmnModel 中解析表单字段权限
        return BpmnModelUtils.parseFormFieldsPermission(bpmnModel, activityId);
    }

    @Override
    public BpmApprovalDetailVo getApprovalDetail(Long userId, BpmApprovalDetailDto bpmApprovalDetailDto) {
        // 1.1 从 bpmApprovalDetailDto 中，读取公共变量
        Long startUserId = userId; // 流程发起人
        HistoricProcessInstance historicProcessInstance = null; // 流程实例
        Integer processInstanceStatus = BpmProcessInstanceStatusEnum.NOT_START.getValue(); // 流程状态
        Map<String, Object> processVariables = bpmApprovalDetailDto.getProcessVariables(); // 流程变量
        // 1.2 如果是流程已发起的场景，则使用流程实例的数据
        if (bpmApprovalDetailDto.getProcessInstanceId() != null) {
            historicProcessInstance = getHistoricProcessInstance(bpmApprovalDetailDto.getProcessInstanceId());
            if (historicProcessInstance == null) {
                throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_NOT_EXISTS);
            }
            startUserId = Long.valueOf(historicProcessInstance.getStartUserId());
            processInstanceStatus = FlowableUtils.getProcessInstanceStatus(historicProcessInstance);
            processVariables = historicProcessInstance.getProcessVariables();
        }
        // 1.3 读取其它相关数据
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(
                historicProcessInstance != null ? historicProcessInstance.getProcessDefinitionId()
                        : bpmApprovalDetailDto.getProcessDefinitionId());
        BpmProcessDefinitionInfo processDefinitionInfo = bpmProcessDefinitionInfoService.getProcessDefinitionInfo(processDefinition.getId());
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        // 2.1 已结束 + 进行中的活动节点
        List<BpmApprovalDetailVo.ActivityNode> endActivityNodes = null; // 已结束的审批信息
        List<BpmApprovalDetailVo.ActivityNode> runActivityNodes = null; // 进行中的审批信息
        List<HistoricActivityInstance> activities = null; // 流程实例列表
        if (bpmApprovalDetailDto.getProcessInstanceId() != null) {
            activities = bpmTaskService.getActivityListByProcessInstanceId(bpmApprovalDetailDto.getProcessInstanceId());
            List<HistoricTaskInstance> tasks = bpmTaskService.getTaskListByProcessInstanceId(bpmApprovalDetailDto.getProcessInstanceId(),
                    true);
            endActivityNodes = getEndActivityNodeList(startUserId, bpmnModel, processDefinitionInfo,
                    historicProcessInstance, processInstanceStatus, activities, tasks);
            runActivityNodes = getRunApproveNodeList(startUserId, bpmnModel, processDefinition, processVariables,
                    activities, tasks);
        }

        // 2.2 流程已经结束，直接 return，无需预测
        if (BpmProcessInstanceStatusEnum.isProcessEndStatus(processInstanceStatus)) {
            return buildApprovalDetail(bpmApprovalDetailDto, bpmnModel, processDefinition, processDefinitionInfo,
                    historicProcessInstance,
                    processInstanceStatus, endActivityNodes, runActivityNodes, null, null);
        }

        // 3.1 计算当前登录用户的待办任务
        // B，会不会表单权限不一致哈。
        BpmTaskVo todoTask = bpmTaskService.getFirstTodoTask(userId, bpmApprovalDetailDto.getProcessInstanceId());

        // 3.2 预测未运行节点的审批信息
        List<BpmApprovalDetailVo.ActivityNode> simulateActivityNodes = getSimulateApproveNodeList(startUserId, bpmnModel,
                processDefinitionInfo,
                processVariables, activities);

        // 4. 拼接最终数据
        return buildApprovalDetail(bpmApprovalDetailDto, bpmnModel, processDefinition, processDefinitionInfo, historicProcessInstance,
                processInstanceStatus, endActivityNodes, runActivityNodes, simulateActivityNodes, todoTask);
    }

    /**
     * 拼接审批详情的最终数据
     * <p>
     * 主要是，拼接审批人的用户信息、部门信息
     */
    private BpmApprovalDetailVo buildApprovalDetail(BpmApprovalDetailDto reqVO,
                                                        BpmnModel bpmnModel,
                                                        ProcessDefinition processDefinition,
                                                        BpmProcessDefinitionInfo processDefinitionInfo,
                                                        HistoricProcessInstance processInstance,
                                                        Integer processInstanceStatus,
                                                        List<BpmApprovalDetailVo.ActivityNode> endApprovalNodeInfos,
                                                        List<BpmApprovalDetailVo.ActivityNode> runningApprovalNodeInfos,
                                                        List<BpmApprovalDetailVo.ActivityNode> simulateApprovalNodeInfos,
                                                        BpmTaskVo todoTask) {
        // 1. 获取所有需要读取用户信息的 userIds
        List<BpmApprovalDetailVo.ActivityNode> approveNodes = Stream.of(endApprovalNodeInfos, runningApprovalNodeInfos, simulateApprovalNodeInfos)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .toList();
        Set<Long> userIds = BpmProcessInstanceConvert.INSTANCE.parseUserIds(processInstance, approveNodes, todoTask);
        Map<Long, BpmUserVo> userMap = bpmUserApi.getUserMap(List.copyOf(userIds));
        Map<Long, BpmDeptVo> deptMap = bpmDeptApi.getDeptMap(userMap.values().stream().map(BpmUserVo::getDeptId).collect(Collectors.toSet()));

        // 2. 表单权限
        String taskId = reqVO.getTaskId() == null && todoTask != null ? todoTask.getId() : reqVO.getTaskId();
        Map<String, String> formFieldsPermission = getFormFieldsPermission(bpmnModel, reqVO.getActivityId(), taskId);

        // 3. 拼接数据
        return BpmProcessInstanceConvert.INSTANCE.buildApprovalDetail(bpmnModel, processDefinition,
                processDefinitionInfo, processInstance,
                processInstanceStatus, approveNodes, todoTask, formFieldsPermission, userMap, deptMap);
    }

    /**
     * 获得【已结束】的活动节点们
     */
    private List<BpmApprovalDetailVo.ActivityNode> getEndActivityNodeList(Long startUserId, BpmnModel bpmnModel,
                                                                          BpmProcessDefinitionInfo processDefinitionInfo,
                                                                          HistoricProcessInstance historicProcessInstance, Integer processInstanceStatus,
                                                                          List<HistoricActivityInstance> activities, List<HistoricTaskInstance> tasks) {
        // 遍历 tasks 列表，只处理已结束的 UserTask
        // 为什么不通过 activities 呢？因为，加签场景下，它只存在于 tasks，没有 activities，导致如果遍历 activities
        // 的话，它无法成为一个节点
        List<HistoricTaskInstance> endTasks = tasks.stream().filter(task -> task.getEndTime() != null).toList();
        List<BpmApprovalDetailVo.ActivityNode> approvalNodes = endTasks.stream().map(task -> {
            FlowElement flowNode = BpmnModelUtils.getFlowElementById(bpmnModel, task.getTaskDefinitionKey());
            BpmApprovalDetailVo.ActivityNode activityNode = new BpmApprovalDetailVo.ActivityNode().setId(task.getTaskDefinitionKey()).setName(task.getName())
                    .setNodeType(BpmnModelConstants.START_USER_NODE_ID.equals(task.getTaskDefinitionKey())
                            ? BpmSimpleModelNodeTypeEnum.START_USER_NODE.getValue()
                            : BpmSimpleModelNodeTypeEnum.APPROVE_NODE.getValue())
                    .setStatus(FlowableUtils.getTaskStatus(task))
                    .setCandidateStrategy(BpmnModelUtils.parseCandidateStrategy(flowNode))
                    .setStartTime(DateUtil.toLocalDateTime(task.getCreateTime())).setEndTime(DateUtil.toLocalDateTime(task.getEndTime()))
                    .setTasks(singletonList(BpmProcessInstanceConvert.INSTANCE.buildApprovalTaskInfo(task)));
            // 如果是取消状态，则跳过
            if (BpmTaskStatusEnum.isCancelStatus(activityNode.getStatus())) {
                return null;
            }
            return activityNode;
        }).collect(Collectors.toList());

        // 遍历 activities，只处理已结束的 StartEvent、EndEvent
        List<HistoricActivityInstance> endActivities = activities.stream().filter(activity -> activity.getEndTime() != null
                && (StrUtil.equalsAny(activity.getActivityType(), ELEMENT_EVENT_START, ELEMENT_EVENT_END))).toList();
        endActivities.forEach(activity -> {
            // StartEvent：只处理 BPMN 的场景。因为，SIMPLE 情况下，已经有 START_USER_NODE 节点
            if (ELEMENT_EVENT_START.equals(activity.getActivityType())
                    && BpmModelType.BPMN.getValue().equals(processDefinitionInfo.getModelType())) {
                BpmApprovalDetailVo.ActivityNodeTask startTask = new BpmApprovalDetailVo.ActivityNodeTask().setId(BpmnModelConstants.START_USER_NODE_ID)
                        .setAssignee(startUserId).setStatus(BpmTaskStatusEnum.APPROVE.getStatus());
                BpmApprovalDetailVo.ActivityNode startNode = new BpmApprovalDetailVo.ActivityNode().setId(startTask.getId())
                        .setName(BpmSimpleModelNodeTypeEnum.START_USER_NODE.getDesc())
                        .setNodeType(BpmSimpleModelNodeTypeEnum.START_USER_NODE.getValue())
                        .setStatus(startTask.getStatus()).setTasks(ListUtil.of(startTask))
                        .setStartTime(DateUtil.toLocalDateTime(activity.getStartTime()))
                        .setEndTime(DateUtil.toLocalDateTime(activity.getEndTime()));
                approvalNodes.add(0, startNode);
                return;
            }
            // EndEvent
            if (ELEMENT_EVENT_END.equals(activity.getActivityType())) {
                if (BpmProcessInstanceStatusEnum.isRejectStatus(processInstanceStatus)) {
                    // 拒绝情况下，不需要展示 EndEvent 结束节点。原因是：前端已经展示 x 效果，无需重复展示
                    return;
                }
                BpmApprovalDetailVo.ActivityNode endNode = new BpmApprovalDetailVo.ActivityNode().setId(activity.getId())
                        .setName(BpmSimpleModelNodeTypeEnum.END_NODE.getDesc())
                        .setNodeType(BpmSimpleModelNodeTypeEnum.END_NODE.getValue()).setStatus(processInstanceStatus)
                        .setStartTime(DateUtil.toLocalDateTime(activity.getStartTime()))
                        .setEndTime(DateUtil.toLocalDateTime(activity.getEndTime()));
                String reason = FlowableUtils.getProcessInstanceReason(historicProcessInstance);
                if (StrUtil.isNotEmpty(reason)) {
                    endNode.setTasks(singletonList(new BpmApprovalDetailVo.ActivityNodeTask().setId(endNode.getId())
                            .setStatus(endNode.getStatus()).setReason(reason)));
                }
                approvalNodes.add(endNode);
            }
        });
        return approvalNodes;
    }

    /**
     * 获得【进行中】的活动节点们
     */
    private List<BpmApprovalDetailVo.ActivityNode> getRunApproveNodeList(Long startUserId,
                                                                         BpmnModel bpmnModel,
                                                                         ProcessDefinition processDefinition,
                                                                         Map<String, Object> processVariables,
                                                                         List<HistoricActivityInstance> activities,
                                                                         List<HistoricTaskInstance> tasks) {
        // 构建运行中的任务，基于 activityId 分组
        if (CollUtil.isEmpty(activities)) {
            return new ArrayList<>();
        }
        List<HistoricActivityInstance> runActivities = activities.stream().filter(activity -> activity.getEndTime() == null
                && (StrUtil.equalsAny(activity.getActivityType(), ELEMENT_TASK_USER))).toList();

        Map<String, List<HistoricActivityInstance>> runningTaskMap = runActivities.stream().collect(Collectors.groupingBy(HistoricActivityInstance::getActivityId));

        // 按照 activityId 分组，构建 ApprovalNodeInfo 节点
        Map<String, HistoricTaskInstance> taskMap = tasks.stream().collect(Collectors.toMap(HistoricTaskInstance::getId, Function.identity()));
        return runningTaskMap.entrySet().stream().map(entry -> {
            String activityId = entry.getKey();
            List<HistoricActivityInstance> taskActivities = entry.getValue();
            // 构建活动节点
            FlowElement flowNode = BpmnModelUtils.getFlowElementById(bpmnModel, activityId);
            HistoricActivityInstance firstActivity = CollUtil.getFirst(taskActivities); // 取第一个任务，会签/或签的任务，开始时间相同
            BpmApprovalDetailVo.ActivityNode activityNode = new BpmApprovalDetailVo.ActivityNode().setId(firstActivity.getActivityId())
                    .setName(firstActivity.getActivityName())
                    .setNodeType(BpmSimpleModelNodeTypeEnum.APPROVE_NODE.getValue())
                    .setStatus(BpmTaskStatusEnum.RUNNING.getStatus())
                    .setCandidateStrategy(BpmnModelUtils.parseCandidateStrategy(flowNode))
                    .setStartTime(DateUtil.toLocalDateTime(CollUtil.getFirst(taskActivities).getStartTime()))
                    .setTasks(new ArrayList<>());
            // 处理每个任务的 tasks 属性
            for (HistoricActivityInstance activity : taskActivities) {
                HistoricTaskInstance task = taskMap.get(activity.getTaskId());
                activityNode.getTasks().add(BpmProcessInstanceConvert.INSTANCE.buildApprovalTaskInfo(task));
                // 加签子任务，需要过滤掉已经完成的加签子任务
                List<HistoricTaskInstance> historicTaskInstanceList = bpmTaskService.getAllChildrenTaskListByParentTaskId(activity.getTaskId(), tasks);
                List<HistoricTaskInstance> childrenTasks = historicTaskInstanceList.stream().filter(childTask -> childTask.getEndTime() == null).toList();
                if (CollUtil.isNotEmpty(childrenTasks)) {
                    activityNode.getTasks().addAll(childrenTasks.stream().map(BpmProcessInstanceConvert.INSTANCE::buildApprovalTaskInfo).toList());
                }
            }
            // 处理每个任务的 candidateUsers 属性：如果是依次审批，需要预测它的后续审批人。因为 Task 是审批完一个，创建一个新的 Task
            if (BpmnModelUtils.isSequentialUserTask(flowNode)) {
                List<Long> candidateUserIds = getTaskCandidateUserList(bpmnModel, flowNode.getId(),
                        startUserId, processDefinition.getId(), processVariables);
                // 截取当前审批人位置后面的候选人，不包含当前审批人
                BpmApprovalDetailVo.ActivityNodeTask approvalTaskInfo = CollUtil.getFirst(activityNode.getTasks());
                Assert.notNull(approvalTaskInfo, "任务不能为空");
                int index = CollUtil.indexOf(candidateUserIds,
                        userId -> Objects.equals(userId, approvalTaskInfo.getOwner())
                                || Objects.equals(userId, approvalTaskInfo.getAssignee())); // 委派或者向前加签情况，需要先比较 owner
                activityNode.setCandidateUserIds(CollUtil.sub(candidateUserIds, index + 1, candidateUserIds.size()));
            }
            return activityNode;
        }).toList();
    }

    /**
     * 获得【预测（未来）】的活动节点们
     */
    private List<BpmApprovalDetailVo.ActivityNode> getSimulateApproveNodeList(Long startUserId, BpmnModel bpmnModel,
                                                                              BpmProcessDefinitionInfo processDefinitionInfo,
                                                                              Map<String, Object> processVariables,
                                                                              List<HistoricActivityInstance> activities) {
        //【可优化】在驳回场景下，未来的预测准确性不高。原因是，驳回后，HistoricActivityInstance
        // 包括了历史的操作，不是只有 startEvent 到当前节点的记录
        if (CollUtil.isEmpty(activities)) {
            activities = new ArrayList<>();
        }
        Set<String> runActivityIds = activities.stream().map(HistoricActivityInstance::getActivityId).collect(Collectors.toSet());
        // 情况一：BPMN 设计器
        if (Objects.equals(BpmModelType.BPMN.getValue(), processDefinitionInfo.getModelType())) {
            List<FlowElement> flowElements = BpmnModelUtils.simulateProcess(bpmnModel, processVariables);
            return flowElements.stream().map(flowElement -> buildNotRunApproveNodeForBpmn(startUserId, bpmnModel,
                    processDefinitionInfo, processVariables, flowElement, runActivityIds)).toList();
        }
        // 情况二：SIMPLE 设计器
        if (Objects.equals(BpmModelType.SIMPLE.getValue(), processDefinitionInfo.getModelType())) {
            BpmSimpleModelNodeVo simpleModel = JsonUtils.readValue(processDefinitionInfo.getSimpleModel(),
                    BpmSimpleModelNodeVo.class);
            List<BpmSimpleModelNodeVo> simpleNodes = SimpleModelUtils.simulateProcess(simpleModel, processVariables);
            return simpleNodes.stream().map(simpleNode -> buildNotRunApproveNodeForSimple(startUserId, bpmnModel,
                    processDefinitionInfo, processVariables, simpleNode, runActivityIds)).toList();
        }
        throw new IllegalArgumentException("未知设计器类型：" + processDefinitionInfo.getModelType());
    }

    private BpmApprovalDetailVo.ActivityNode buildNotRunApproveNodeForSimple(Long startUserId, BpmnModel bpmnModel,
                                                                             BpmProcessDefinitionInfo processDefinitionInfo, Map<String, Object> processVariables,
                                                                             BpmSimpleModelNodeVo node, Set<String> runActivityIds) {
        //【可优化】在驳回场景下，未来的预测准确性不高。原因是，驳回后，HistoricActivityInstance
        // 包括了历史的操作，不是只有 startEvent 到当前节点的记录
        if (runActivityIds.contains(node.getId())) {
            return null;
        }

        BpmApprovalDetailVo.ActivityNode activityNode = new BpmApprovalDetailVo.ActivityNode().setId(node.getId()).setName(node.getName())
                .setNodeType(node.getType()).setCandidateStrategy(node.getCandidateStrategy())
                .setStatus(BpmTaskStatusEnum.NOT_START.getStatus());

        // 1. 开始节点/审批节点
        if (Objects.equals(node.getType(), BpmSimpleModelNodeTypeEnum.START_USER_NODE.getValue())
                || Objects.equals(node.getType(), BpmSimpleModelNodeTypeEnum.APPROVE_NODE.getValue())) {
            List<Long> candidateUserIds = getTaskCandidateUserList(bpmnModel, node.getId(),
                    startUserId, processDefinitionInfo.getProcessDefinitionId(), processVariables);
            activityNode.setCandidateUserIds(candidateUserIds);
            return activityNode;
        }

        // 2. 结束节点
        if (BpmSimpleModelNodeTypeEnum.END_NODE.getValue().equals(node.getType())) {
            return activityNode;
        }

        // 3. 抄送节点
        if (CollUtil.isEmpty(runActivityIds) && // 流程发起时：需要展示抄送节点，用于选择抄送人
                BpmSimpleModelNodeTypeEnum.COPY_NODE.getValue().equals(node.getType())) {
            return activityNode;
        }
        return null;
    }

    private BpmApprovalDetailVo.ActivityNode buildNotRunApproveNodeForBpmn(Long startUserId, BpmnModel bpmnModel,
                                                                           BpmProcessDefinitionInfo processDefinitionInfo, Map<String, Object> processVariables,
                                                                           FlowElement node, Set<String> runActivityIds) {
        if (runActivityIds.contains(node.getId())) {
            return null;
        }
        BpmApprovalDetailVo.ActivityNode activityNode = new BpmApprovalDetailVo.ActivityNode().setId(node.getId())
                .setStatus(BpmTaskStatusEnum.NOT_START.getStatus());

        // 1. 开始节点
        if (node instanceof StartEvent) {
            return activityNode.setName(BpmSimpleModelNodeTypeEnum.START_USER_NODE.getDesc())
                    .setNodeType(BpmSimpleModelNodeTypeEnum.START_USER_NODE.getValue());
        }

        // 2. 审批节点
        if (node instanceof UserTask) {
            List<Long> candidateUserIds = getTaskCandidateUserList(bpmnModel, node.getId(),
                    startUserId, processDefinitionInfo.getProcessDefinitionId(), processVariables);
            return activityNode.setName(node.getName()).setNodeType(BpmSimpleModelNodeTypeEnum.APPROVE_NODE.getValue())
                    .setCandidateStrategy(BpmnModelUtils.parseCandidateStrategy(node))
                    .setCandidateUserIds(candidateUserIds);
        }

        // 3. 结束节点
        if (node instanceof EndEvent) {
            return activityNode.setName(BpmSimpleModelNodeTypeEnum.END_NODE.getDesc())
                    .setNodeType(BpmSimpleModelNodeTypeEnum.END_NODE.getValue());
        }
        return null;
    }

    private List<Long> getTaskCandidateUserList(BpmnModel bpmnModel, String activityId,
                                                Long startUserId, String processDefinitionId, Map<String, Object> processVariables) {
        Set<Long> userIds = taskCandidateInvoker.calculateUsersByActivity(bpmnModel, activityId,
                startUserId, processDefinitionId, processVariables);
        return new ArrayList<>(userIds);
    }

    @Override
    public BpmProcessInstanceBpmnModelViewVo getProcessInstanceBpmnModelView(String id) {
        // 1.1 获得流程实例
        HistoricProcessInstance processInstance = getHistoricProcessInstance(id);
        if (processInstance == null) {
            return null;
        }
        // 1.2 获得流程定义
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        if (bpmnModel == null) {
            return null;
        }
        BpmSimpleModelNodeVo simpleModel = null;
        BpmProcessDefinitionInfo processDefinitionInfo = bpmProcessDefinitionInfoService.getProcessDefinitionInfo(
                processInstance.getProcessDefinitionId());
        if (processDefinitionInfo != null
                && BpmModelType.SIMPLE.getValue().equals(processDefinitionInfo.getModelType())) {
            simpleModel = JsonUtils.readValue(processDefinitionInfo.getSimpleModel(), BpmSimpleModelNodeVo.class);
        }
        // 1.3 获得流程实例对应的活动实例列表 + 任务列表
        List<HistoricActivityInstance> activities = bpmTaskService.getActivityListByProcessInstanceId(id);
        List<HistoricTaskInstance> tasks = bpmTaskService.getTaskListByProcessInstanceId(id, true);

        // 2.1 拼接进度信息
        Set<String> unfinishedTaskActivityIds = activities.stream()
                .filter(activityInstance -> activityInstance.getEndTime() == null)
                .map(HistoricActivityInstance::getActivityId)
                .collect(Collectors.toSet());
        Set<String> finishedTaskActivityIds = activities.stream()
                .filter(activityInstance -> activityInstance.getEndTime() != null
                        && ObjectUtil.notEqual(activityInstance.getActivityType(), BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW)
        ).map(HistoricActivityInstance::getActivityId).collect(Collectors.toSet());
        Set<String> finishedSequenceFlowActivityIds = activities.stream()
                .filter(activityInstance -> activityInstance.getEndTime() != null
                        && ObjectUtil.equals(activityInstance.getActivityType(), BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW))
                .map(HistoricActivityInstance::getActivityId).collect(Collectors.toSet());
        // 特殊：会签情况下，会有部分已完成（审批）、部分未完成（待审批），此时需要 finishedTaskActivityIds 移除掉
        finishedTaskActivityIds.removeAll(unfinishedTaskActivityIds);
        // 特殊：如果流程实例被拒绝，则需要计算是哪个活动节点。
        // 注意，只取最后一个。因为会存在多次拒绝的情况，拒绝驳回到指定节点
        Set<String> rejectTaskActivityIds = CollUtil.newHashSet();
        if (BpmProcessInstanceStatusEnum.isRejectStatus(FlowableUtils.getProcessInstanceStatus(processInstance))) {
            tasks.stream()
                    .filter(task -> BpmTaskStatusEnum.isRejectStatus(FlowableUtils.getTaskStatus(task)))
                    .max(Comparator.comparing(HistoricTaskInstance::getEndTime))
                    .ifPresent(reject -> rejectTaskActivityIds.add(reject.getTaskDefinitionKey()));
            finishedTaskActivityIds.removeAll(rejectTaskActivityIds);
        }

        // 2.2 拼接基础信息
        Set<Long> userIds = BpmProcessInstanceConvert.INSTANCE.parseUserIds02(processInstance, tasks);
        Map<Long, BpmUserVo> userMap = bpmUserApi.getUserMap(List.copyOf(userIds));
        Map<Long, BpmDeptVo> deptMap = bpmDeptApi.getDeptMap(userMap.values().stream().map(BpmUserVo::getDeptId).collect(Collectors.toSet()));
        return BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceBpmnModelView(processInstance, tasks, bpmnModel,
                simpleModel,
                unfinishedTaskActivityIds, finishedTaskActivityIds, finishedSequenceFlowActivityIds,
                rejectTaskActivityIds,
                userMap, deptMap);
    }

    @Override
    public void cancelProcessInstanceByStartUser(Long userId, BpmProcessInstanceCancelDto cancelReqVO) {
        // 1.1 校验流程实例存在
        ProcessInstance instance = getProcessInstance(cancelReqVO.getId());
        if (instance == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }
        // 1.2 只能取消自己的
        if (!Objects.equals(instance.getStartUserId(), String.valueOf(userId))) {
            throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF);
        }
        // 1.3 校验允许撤销审批中的申请
        BpmProcessDefinitionInfo processDefinitionInfo = bpmProcessDefinitionInfoService.getProcessDefinitionInfo(instance.getProcessDefinitionId());
        Assert.notNull(processDefinitionInfo, "流程定义({})不存在", processDefinitionInfo);
        if (processDefinitionInfo.getAllowCancelRunningProcess() != null // 防止未配置 AllowCancelRunningProcess , 默认为可取消
                && Boolean.FALSE.equals(processDefinitionInfo.getAllowCancelRunningProcess())) {
            throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_CANCEL_FAIL_NOT_ALLOW);
        }

        // 2. 取消流程
        updateProcessInstanceCancel(cancelReqVO.getId(), BpmReasonEnum.CANCEL_PROCESS_INSTANCE_BY_START_USER.format(cancelReqVO.getReason()));
    }

    @Override
    public void cancelProcessInstanceByAdmin(Long userId, BpmProcessInstanceCancelDto cancelReqVO) {
        // 1.1 校验流程实例存在
        ProcessInstance instance = getProcessInstance(cancelReqVO.getId());
        if (instance == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }

        // 2. 取消流程
        BpmUserVo user = bpmUserApi.getUser(userId);
        updateProcessInstanceCancel(cancelReqVO.getId(),
                BpmReasonEnum.CANCEL_PROCESS_INSTANCE_BY_ADMIN.format(user.getNickname(), cancelReqVO.getReason()));
    }

    private void updateProcessInstanceCancel(String id, String reason) {
        // 1. 更新流程实例 status
        runtimeService.setVariable(id, BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS,
                BpmProcessInstanceStatusEnum.CANCEL.getValue());
        runtimeService.setVariable(id, BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_REASON, reason);

        // 2. 结束流程
        bpmTaskService.moveTaskToEnd(id, reason);
    }

    @Override
    public void updateProcessInstanceReject(ProcessInstance processInstance, String reason) {
        runtimeService.setVariable(processInstance.getProcessInstanceId(),
                BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS,
                BpmProcessInstanceStatusEnum.REJECT.getValue());
        runtimeService.setVariable(processInstance.getProcessInstanceId(),
                BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_REASON,
                BpmReasonEnum.REJECT_TASK.format(reason));
    }

    // ========== Event 事件相关方法 ==========

    @Override
    public void processProcessInstanceCompleted(ProcessInstance instance) {
        // 注意：需要基于 instance 设置租户编号，避免 Flowable 内部异步时，丢失租户编号
        FlowableUtils.execute(instance.getTenantId(), () -> {
            // 1.1 获取当前状态
            Integer status = (Integer) instance.getProcessVariables()
                    .get(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS);
            String reason = (String) instance.getProcessVariables()
                    .get(BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_REASON);
            // 1.2 当流程状态还是审批状态中，说明审批通过了，则变更下它的状态
            // 为什么这么处理？因为流程完成，并且完成了，说明审批通过了
            if (Objects.equals(status, BpmProcessInstanceStatusEnum.RUNNING.getValue())) {
                status = BpmProcessInstanceStatusEnum.APPROVE.getValue();
                runtimeService.setVariable(instance.getId(), BpmnVariableConstants.PROCESS_INSTANCE_VARIABLE_STATUS,
                        status);
            }

            // 2. 发送对应的消息通知
            if (Objects.equals(status, BpmProcessInstanceStatusEnum.APPROVE.getValue())) {
                bpmMessageService.sendMessageWhenProcessInstanceApprove(
                        BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceApproveMessage(instance));
            } else if (Objects.equals(status, BpmProcessInstanceStatusEnum.REJECT.getValue())) {
                bpmMessageService.sendMessageWhenProcessInstanceReject(
                        BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceRejectMessage(instance, reason));
            }

            // 3. 发送流程实例的状态事件
            processInstanceEventPublisher.sendProcessInstanceResultEvent(
                    BpmProcessInstanceConvert.INSTANCE.buildProcessInstanceStatusEvent(this, instance, status));
        });
    }

    @Override
    public void updateProcessInstanceVariables(String id, Map<String, Object> variables) {
        runtimeService.setVariables(id, variables);
    }

    @Override
    public PageResult<BpmProcessInstanceVo> managerPage(BpmProcessInstancePageDto bpmProcessInstancePageDto) {
        PageResult<HistoricProcessInstance> processInstancePage = getProcessInstancePage(null, bpmProcessInstancePageDto);
        if (CollUtil.isEmpty(processInstancePage.getList())) {
            return PageResult.empty();
        }

        List<HistoricProcessInstance> historicProcessInstances = processInstancePage.getList();

        // 拼接返回
        List<String> processInstanceIds = historicProcessInstances.stream().map(HistoricProcessInstance::getId).toList();
        Map<String, List<Task>> taskMap = bpmTaskService.getTaskMapByProcessInstanceIds(processInstanceIds);

        List<String> processDefinitionIds = historicProcessInstances.stream().map(HistoricProcessInstance::getProcessDefinitionId).toList();
        Map<String, ProcessDefinition> processDefinitionMap = bpmProcessDefinitionService.getProcessDefinitionMap(processDefinitionIds);

        List<Long> categoryIds = processDefinitionMap.values().stream().map(ProcessDefinition::getCategory).map(Long::parseLong).toList();
        Map<Long, String> categoryNameMap = bpmCategoryService.getCategoryNameMap(categoryIds);

        // 发起人信息
        List<Long> userIds = processInstancePage.getList().stream().map(HistoricProcessInstance::getStartUserId).map(userId -> NumberUtil.parseLong(userId, null)).filter(Objects::nonNull).toList();
        Map<Long, BpmUserVo> userMap = bpmUserApi.getUserMap(userIds);

        List<Long> deptIds = userMap.values().stream().map(BpmUserVo::getDeptId).toList();
        Map<Long, BpmDeptVo> deptMap = bpmDeptApi.getDeptMap(deptIds);

        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(processDefinitionIds);
        return PageResult.of(processInstancePage.getTotal(), BpmProcessInstanceConvert.INSTANCE.buildProcessInstances(historicProcessInstances,
                processDefinitionMap, categoryNameMap, taskMap, userMap, deptMap, processDefinitionInfoMap));
    }

    @Override
    public PageResult<BpmProcessInstanceVo> myPage(BpmProcessInstancePageDto bpmProcessInstancePageDto) {
        PageResult<HistoricProcessInstance> processInstancePage = getProcessInstancePage(LoginUserContext.currentUserId(), bpmProcessInstancePageDto);
        if (CollUtil.isEmpty(processInstancePage.getList())) {
            return PageResult.empty();
        }

        List<HistoricProcessInstance> historicProcessInstances = processInstancePage.getList();

        // 拼接返回
        List<String> processInstanceIds = historicProcessInstances.stream().map(HistoricProcessInstance::getId).toList();
        Map<String, List<Task>> taskMap = bpmTaskService.getTaskMapByProcessInstanceIds(processInstanceIds);

        List<String> processDefinitionIds = historicProcessInstances.stream().map(HistoricProcessInstance::getProcessDefinitionId).toList();
        Map<String, ProcessDefinition> processDefinitionMap = bpmProcessDefinitionService.getProcessDefinitionMap(processDefinitionIds);

        List<Long> categoryIds = processDefinitionMap.values().stream().map(ProcessDefinition::getCategory).map(Long::parseLong).toList();
        Map<Long, String> categoryNameMap = bpmCategoryService.getCategoryNameMap(categoryIds);

        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(processDefinitionIds);
        return PageResult.of(processInstancePage.getTotal(), BpmProcessInstanceConvert.INSTANCE.buildProcessInstances(historicProcessInstances,
                processDefinitionMap, categoryNameMap, taskMap, null, null, processDefinitionInfoMap));
    }
}

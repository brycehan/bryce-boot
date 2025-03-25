package com.brycehan.boot.bpm.entity.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.common.BpmProcessInstanceStatusEvent;
import com.brycehan.boot.bpm.common.BpmnModelUtils;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenProcessInstanceApproveDto;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenProcessInstanceRejectDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.*;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程定义信息转换器
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BpmProcessInstanceConvert {

    BpmProcessInstanceConvert INSTANCE = Mappers.getMapper(BpmProcessInstanceConvert.class);

    default List<BpmProcessInstanceVo> buildProcessInstances(List<HistoricProcessInstance> historicProcessInstances,
                                                             Map<String, ProcessDefinition> processDefinitionMap,
                                                             Map<Long, String> categoryNameMap,
                                                             Map<String, List<Task>> taskMap,
                                                             Map<Long, BpmUserVo> userMap,
                                                             Map<Long, BpmDeptVo> deptMap,
                                                             Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap) {
        List<BpmProcessInstanceVo> bpmProcessInstanceVoList = BeanUtil.copyToList(historicProcessInstances, BpmProcessInstanceVo.class);
        for (int i = 0; i < bpmProcessInstanceVoList.size(); i++) {
            BpmProcessInstanceVo bpmProcessInstanceVo = bpmProcessInstanceVoList.get(i);
            bpmProcessInstanceVo.setStatus(FlowableUtils.getProcessInstanceStatus(historicProcessInstances.get(i)));

            ProcessDefinition processDefinition = processDefinitionMap.get(bpmProcessInstanceVo.getProcessDefinitionId());
            if (processDefinition != null) {
                bpmProcessInstanceVo.setCategory(processDefinition.getCategory())
                        .setProcessDefinition(BeanUtil.toBean(processDefinition, BpmProcessDefinitionVo.class));
            }
            String categoryName = categoryNameMap.get(NumberUtil.parseLong(bpmProcessInstanceVo.getCategory(), null));
            Optional.ofNullable(categoryName).ifPresent(bpmProcessInstanceVo::setCategoryName);

            bpmProcessInstanceVo.setTasks(BeanUtil.copyToList(taskMap.get(bpmProcessInstanceVo.getId()), BpmProcessInstanceVo.Task.class));
            // user
            if (userMap != null) {
                BpmUserVo startUser = userMap.get(NumberUtil.parseLong(historicProcessInstances.get(i).getStartUserId(), null));
                if (startUser != null) {
                    bpmProcessInstanceVo.setStartUser(BeanUtil.toBean(startUser, UserSimpleBaseVo.class));
                    BpmDeptVo bpmDeptVo = deptMap.get(startUser.getOrgId());
                    if (bpmDeptVo != null) {
                        bpmProcessInstanceVo.getStartUser().setDeptName(bpmDeptVo.getName());
                    }
                }
            }
            // 摘要
            bpmProcessInstanceVo.setSummary(FlowableUtils.getSummary(processDefinitionInfoMap.get(bpmProcessInstanceVo.getProcessDefinitionId()),
                    historicProcessInstances.get(i).getProcessVariables()));
            // 表单
            bpmProcessInstanceVo.setFormVariables(historicProcessInstances.get(i).getProcessVariables());
        }
        return bpmProcessInstanceVoList;
    }

    default BpmProcessInstanceVo buildProcessInstance(HistoricProcessInstance processInstance,
                                                          ProcessDefinition processDefinition,
                                                          BpmProcessDefinitionInfo processDefinitionInfo,
                                                          BpmUserVo startUser,
                                                          BpmDeptVo dept) {
        BpmProcessInstanceVo bpmProcessInstanceVo = BeanUtil.copyProperties(processInstance, BpmProcessInstanceVo.class);
        bpmProcessInstanceVo.setStatus(FlowableUtils.getProcessInstanceStatus(processInstance))
                .setFormVariables(FlowableUtils.getProcessInstanceFormVariable(processInstance));
        // definition
        bpmProcessInstanceVo.setProcessDefinition(BeanUtil.copyProperties(processDefinition, BpmProcessDefinitionVo.class));
        copyTo(processDefinitionInfo, bpmProcessInstanceVo.getProcessDefinition());
        // user
        if (startUser != null) {
            bpmProcessInstanceVo.setStartUser(BeanUtil.toBean(startUser, UserSimpleBaseVo.class));
            Optional.ofNullable(dept).ifPresent(bpmDeptVo -> bpmProcessInstanceVo.getStartUser().setDeptName(bpmDeptVo.getName()));
        }
        return bpmProcessInstanceVo;
    }

    @Mapping(source = "from.id", target = "to.id", ignore = true)
    void copyTo(BpmProcessDefinitionInfo from, @MappingTarget BpmProcessDefinitionVo to);

    default BpmProcessInstanceStatusEvent buildProcessInstanceStatusEvent(Object source, ProcessInstance instance, Integer status) {
        return new BpmProcessInstanceStatusEvent(source).setId(instance.getId()).setStatus(status)
                .setProcessDefinitionKey(instance.getProcessDefinitionKey()).setBusinessKey(instance.getBusinessKey());
    }

    default BpmMessageSendWhenProcessInstanceApproveDto buildProcessInstanceApproveMessage(ProcessInstance instance) {
        return new BpmMessageSendWhenProcessInstanceApproveDto()
                .setStartUserId(NumberUtil.parseLong(instance.getStartUserId(), null))
                .setProcessInstanceId(instance.getId())
                .setProcessInstanceName(instance.getName());
    }

    default BpmMessageSendWhenProcessInstanceRejectDto buildProcessInstanceRejectMessage(ProcessInstance instance, String reason) {
        return new BpmMessageSendWhenProcessInstanceRejectDto()
                .setProcessInstanceName(instance.getName())
                .setProcessInstanceId(instance.getId())
                .setReason(reason)
                .setStartUserId(NumberUtil.parseLong(instance.getStartUserId(), null));
    }

    default BpmProcessInstanceBpmnModelViewVo buildProcessInstanceBpmnModelView(HistoricProcessInstance processInstance,
                                                                                List<HistoricTaskInstance> taskInstances,
                                                                                BpmnModel bpmnModel,
                                                                                BpmSimpleModelNodeVo simpleModel,
                                                                                Set<String> unfinishedTaskActivityIds,
                                                                                Set<String> finishedTaskActivityIds,
                                                                                Set<String> finishedSequenceFlowActivityIds,
                                                                                Set<String> rejectTaskActivityIds,
                                                                                Map<Long, BpmUserVo> userMap,
                                                                                Map<Long, BpmDeptVo> deptMap) {
        BpmProcessInstanceBpmnModelViewVo bpmProcessInstanceBpmnModelViewVo = new BpmProcessInstanceBpmnModelViewVo();
        // 基本信息
        BpmProcessInstanceVo bpmProcessInstanceVo = BeanUtil.toBean(processInstance, BpmProcessInstanceVo.class);
        if (bpmProcessInstanceVo != null) {
            bpmProcessInstanceVo.setStatus(FlowableUtils.getProcessInstanceStatus(processInstance));
            bpmProcessInstanceVo.setStartUser(buildUser(processInstance.getStartUserId(), userMap, deptMap));
            bpmProcessInstanceBpmnModelViewVo.setProcessInstance(bpmProcessInstanceVo);
        }

        List<BpmTaskVo> bpmTaskVoList = taskInstances.stream().map(task -> {
            BpmTaskVo bean = BeanUtil.toBean(task, BpmTaskVo.class);
            bean.setStatus(FlowableUtils.getTaskStatus(task))
                    .setReason(FlowableUtils.getTaskReason(task))
                    .setAssigneeUser(buildUser(task.getAssignee(), userMap, deptMap))
                    .setOwnerUser(buildUser(task.getOwner(), userMap, deptMap));
            return bean;
        }).toList();
        bpmProcessInstanceBpmnModelViewVo.setTasks(bpmTaskVoList);
        bpmProcessInstanceBpmnModelViewVo.setBpmnXml(BpmnModelUtils.getBpmnXml(bpmnModel));
        bpmProcessInstanceBpmnModelViewVo.setSimpleModel(simpleModel);
        // 进度信息
        bpmProcessInstanceBpmnModelViewVo.setUnfinishedTaskActivityIds(unfinishedTaskActivityIds)
                .setFinishedTaskActivityIds(finishedTaskActivityIds)
                .setFinishedSequenceFlowActivityIds(finishedSequenceFlowActivityIds)
                .setRejectedTaskActivityIds(rejectTaskActivityIds);
        return bpmProcessInstanceBpmnModelViewVo;
    }

    default UserSimpleBaseVo buildUser(String userIdStr,
                                       Map<Long, BpmUserVo> userMap,
                                       Map<Long, BpmDeptVo> deptMap) {
        if (StrUtil.isEmpty(userIdStr)) {
            return null;
        }
        Long userId = NumberUtil.parseLong(userIdStr, null);
        return buildUser(userId, userMap, deptMap);
    }

    default UserSimpleBaseVo buildUser(Long userId,
                                       Map<Long, BpmUserVo> userMap,
                                       Map<Long, BpmDeptVo> deptMap) {
        if (userId == null) {
            return null;
        }
        BpmUserVo user = userMap.get(userId);
        if (user == null) {
            return null;
        }
        UserSimpleBaseVo userVO = BeanUtil.toBean(user, UserSimpleBaseVo.class);
        BpmDeptVo dept = user.getOrgId() != null ? deptMap.get(user.getOrgId()) : null;
        if (dept != null) {
            userVO.setDeptName(dept.getName());
        }
        return userVO;
    }

    default BpmApprovalDetailVo.ActivityNodeTask buildApprovalTaskInfo(HistoricTaskInstance task) {
        if (task == null) {
            return null;
        }
        return BeanUtil.toBean(task, BpmApprovalDetailVo.ActivityNodeTask.class)
                .setStatus(FlowableUtils.getTaskStatus(task)).setReason(FlowableUtils.getTaskReason(task))
                .setSignPicUrl(FlowableUtils.getTaskSignPicUrl(task));
    }

    default Set<Long> parseUserIds(HistoricProcessInstance processInstance,
                                   List<BpmApprovalDetailVo.ActivityNode> activityNodes,
                                   BpmTaskVo todoTask) {
        Set<Long> userIds = new HashSet<>();
        if (processInstance != null) {
            userIds.add(NumberUtil.parseLong(processInstance.getStartUserId(), null));
        }
        for (BpmApprovalDetailVo.ActivityNode activityNode : activityNodes) {
            List<BpmApprovalDetailVo.ActivityNodeTask> tasks = activityNode.getTasks();
            if (CollUtil.isNotEmpty(tasks)) {
                CollUtil.addAll(userIds, tasks.stream().map(BpmApprovalDetailVo.ActivityNodeTask::getAssignee).collect(Collectors.toSet()));
                CollUtil.addAll(userIds, tasks.stream().map(BpmApprovalDetailVo.ActivityNodeTask::getOwner).collect(Collectors.toSet()));
            }
            CollUtil.addAll(userIds, activityNode.getCandidateUserIds());
        }
        if (todoTask != null) {
            CollUtil.addIfAbsent(userIds, todoTask.getAssignee());
            CollUtil.addIfAbsent(userIds, todoTask.getOwner());
            if (CollUtil.isNotEmpty(todoTask.getChildren())) {
                CollUtil.addAll(userIds, todoTask.getChildren().stream().map(BpmTaskVo::getAssignee).collect(Collectors.toSet()));
                CollUtil.addAll(userIds, todoTask.getChildren().stream().map(BpmTaskVo::getOwner).collect(Collectors.toSet()));
            }
        }
        return userIds.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    default Set<Long> parseUserIds02(HistoricProcessInstance processInstance,
                                     List<HistoricTaskInstance> tasks) {

        Set<Long> userIds = CollUtil.newHashSet(Long.valueOf(processInstance.getStartUserId()));
        tasks.forEach(task -> {
            CollUtil.addIfAbsent(userIds, NumberUtil.parseLong((task.getAssignee()), null));
            CollUtil.addIfAbsent(userIds, NumberUtil.parseLong((task.getOwner()), null));
        });
        return userIds;
    }

    default BpmApprovalDetailVo buildApprovalDetail(BpmnModel bpmnModel,
                                                        ProcessDefinition processDefinition,
                                                        BpmProcessDefinitionInfo processDefinitionInfo,
                                                        HistoricProcessInstance processInstance,
                                                        Integer processInstanceStatus,
                                                        List<BpmApprovalDetailVo.ActivityNode> activityNodes,
                                                        BpmTaskVo todoTask,
                                                        Map<String, String> formFieldsPermission,
                                                        Map<Long, BpmUserVo> userMap,
                                                        Map<Long, BpmDeptVo> deptMap) {
        // 1.1 流程实例
        BpmProcessInstanceVo processInstanceResp = null;
        if (processInstance != null) {
            BpmUserVo startUser = userMap.get(NumberUtil.parseLong(processInstance.getStartUserId(), null));
            BpmDeptVo dept = startUser != null ? deptMap.get(startUser.getOrgId()) : null;
            processInstanceResp = buildProcessInstance(processInstance, null, null, startUser, dept);
        }

        // 1.2 流程定义
        BpmProcessDefinitionVo definitionResp = BpmProcessDefinitionConvert.INSTANCE.buildProcessDefinition(
                processDefinition, null, processDefinitionInfo, null, null, bpmnModel);

        // 1.3 流程节点
        activityNodes.forEach(approveNode -> {
            if (approveNode.getTasks() != null) {
                approveNode.getTasks().forEach(task -> {
                    task.setAssigneeUser(buildUser(task.getAssignee(), userMap, deptMap));
                    task.setOwnerUser(buildUser(task.getOwner(), userMap, deptMap));
                });
            }

            List<UserSimpleBaseVo> candidateUsers = new ArrayList<>();
            if (CollUtil.isNotEmpty(approveNode.getCandidateUserIds())) {
                candidateUsers = approveNode.getCandidateUserIds().stream().map(userId -> buildUser(userId, userMap, deptMap)).filter(Objects::nonNull).toList();
            }
            approveNode.setCandidateUsers(candidateUsers);
        });

        // 1.4 待办任务
        if (todoTask != null) {
            todoTask.setAssigneeUser(buildUser(todoTask.getAssignee(), userMap, deptMap));
            todoTask.setOwnerUser(buildUser(todoTask.getOwner(), userMap, deptMap));
            if (CollUtil.isNotEmpty(todoTask.getChildren())) {
                todoTask.getChildren().forEach(childTask -> {
                    childTask.setAssigneeUser(buildUser(childTask.getAssignee(), userMap, deptMap));
                    childTask.setOwnerUser(buildUser(childTask.getOwner(), userMap, deptMap));
                });
            }
        }

        // 2. 拼接起来
        return new BpmApprovalDetailVo().setStatus(processInstanceStatus)
                .setProcessDefinition(definitionResp)
                .setProcessInstance(processInstanceResp)
                .setFormFieldsPermission(formFieldsPermission)
                .setTodoTask(todoTask)
                .setActivityNodes(activityNodes);
    }

}
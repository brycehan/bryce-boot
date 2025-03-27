package com.brycehan.boot.bpm.entity.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.common.type.BpmTaskStatusEnum;
import com.brycehan.boot.bpm.entity.dto.BpmMessageSendWhenTaskCreatedDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmTaskVo;
import com.brycehan.boot.bpm.entity.vo.BpmUserSimpleBaseVo;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.*;

/**
 * Bpm 任务 Convert
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Mapper
public interface BpmTaskConvert {

    BpmTaskConvert INSTANCE = Mappers.getMapper(BpmTaskConvert.class);

    default List<BpmTaskVo> buildTodoTaskPage(List<Task> pageResult,
                                                    Map<String, ProcessInstance> processInstanceMap,
                                                    Map<Long, BpmUserVo> userMap,
                                                    Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap) {
        List<BpmTaskVo> bpmTaskVoList = BeanUtil.copyToList(pageResult, BpmTaskVo.class);
        bpmTaskVoList.forEach(taskVO -> {
            ProcessInstance processInstance = processInstanceMap.get(taskVO.getProcessInstanceId());
            if (processInstance == null) {
                return;
            }
            taskVO.setProcessInstance(BeanUtil.toBean(processInstance, BpmTaskVo.ProcessInstance.class));
            BpmUserVo startUser = userMap.get(NumberUtil.parseLong(processInstance.getStartUserId(), null));
            taskVO.getProcessInstance().setStartUser(BeanUtil.toBean(startUser, BpmUserSimpleBaseVo.class));
            // 摘要
            taskVO.getProcessInstance().setSummary(FlowableUtils.getSummary(processDefinitionInfoMap.get(processInstance.getProcessDefinitionId()),
                    processInstance.getProcessVariables()));
        });
        return bpmTaskVoList;
    }

    default List<BpmTaskVo> buildTaskPage(List<HistoricTaskInstance> pageResult,
                                                    Map<String, HistoricProcessInstance> processInstanceMap,
                                                    Map<Long, BpmUserVo> userMap,
                                                    Map<Long, BpmDeptVo> deptMap,
                                                    Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap) {
        return pageResult.stream().map(task -> {
            BpmTaskVo taskVO = BeanUtil.toBean(task, BpmTaskVo.class);
            taskVO.setStatus(FlowableUtils.getTaskStatus(task)).setReason(FlowableUtils.getTaskReason(task));
            // 用户信息
            BpmUserVo assignUser = userMap.get(NumberUtil.parseLong(task.getAssignee(), null));
            if (assignUser != null) {
                taskVO.setAssigneeUser(BeanUtil.toBean(assignUser, BpmUserSimpleBaseVo.class));
                Optional.ofNullable(deptMap).map(map -> map.get(assignUser.getDeptId()))
                        .ifPresent(bpmDeptVo -> taskVO.getAssigneeUser().setDeptName(bpmDeptVo.getName()));
//                BpmDeptVo bpmDeptVo = deptMap.get(assignUser.getDeptId());
//                if (bpmDeptVo != null) {
//                    taskVO.getAssigneeUser().setDeptName(bpmDeptVo.getName());
//                }
            }
            // 流程实例
            HistoricProcessInstance processInstance = processInstanceMap.get(taskVO.getProcessInstanceId());
            if (processInstance != null) {
                BpmUserVo startUser = userMap.get(NumberUtil.parseLong(processInstance.getStartUserId(), null));
                taskVO.setProcessInstance(BeanUtil.toBean(processInstance, BpmTaskVo.ProcessInstance.class));
                taskVO.getProcessInstance().setStartUser(BeanUtil.toBean(startUser, BpmUserSimpleBaseVo.class));
                // 摘要
                taskVO.getProcessInstance().setSummary(FlowableUtils.getSummary(processDefinitionInfoMap.get(processInstance.getProcessDefinitionId()),
                        processInstance.getProcessVariables()));
            }
            return taskVO;
        }).toList();
    }

    default List<BpmTaskVo> buildTaskListByProcessInstanceId(List<HistoricTaskInstance> taskList,
                                                                 Map<Long, BpmForm> formMap,
                                                                 Map<Long, BpmUserVo> userMap,
                                                                 Map<Long, BpmDeptVo> deptMap) {
        return taskList.stream().map(task -> {
            
            // 特殊：已取消的任务，不返回
            BpmTaskVo taskVO = BeanUtil.toBean(task, BpmTaskVo.class);
            Integer taskStatus = FlowableUtils.getTaskStatus(task);
            if (BpmTaskStatusEnum.isCancelStatus(taskStatus)) {
                return null;
            }
            taskVO.setStatus(taskStatus).setReason(FlowableUtils.getTaskReason(task));
            // 表单信息
            BpmForm form = MapUtil.get(formMap, NumberUtil.parseLong(task.getFormKey(), null), BpmForm.class);
            if (form != null) {
                taskVO.setFormId(form.getId()).setFormName(form.getName()).setFormConf(form.getConf())
                        .setFormFields(form.getFields()).setFormVariables(FlowableUtils.getTaskFormVariable(task));
            }
            // 用户信息
            buildTaskAssignee(taskVO, task.getAssignee(), userMap, deptMap);
            buildTaskOwner(taskVO, task.getOwner(), userMap, deptMap);
            return taskVO;
        }).filter(Objects::nonNull).toList();
    }

    default List<BpmTaskVo> buildTaskListByParentTaskId(List<Task> taskList,
                                                            Map<Long, BpmUserVo> userMap,
                                                            Map<Long, BpmDeptVo> deptMap) {
        return taskList.stream().map( task -> {
            BpmTaskVo taskVO = BeanUtil.toBean(task, BpmTaskVo.class);
            if (taskVO != null) {
                BpmUserVo assignUser = userMap.get(NumberUtil.parseLong(task.getAssignee(), null));
                if (assignUser != null) {
                    taskVO.setAssigneeUser(BeanUtil.toBean(assignUser, BpmUserSimpleBaseVo.class));
                    BpmDeptVo dept = deptMap.get(assignUser.getDeptId());
                    if (dept != null) {
                        taskVO.getAssigneeUser().setDeptName(dept.getName());
                    }
                }
                BpmUserVo ownerUser = userMap.get(NumberUtil.parseLong(task.getOwner(), null));
                if (ownerUser != null) {
                    taskVO.setOwnerUser(BeanUtil.toBean(ownerUser, BpmUserSimpleBaseVo.class));
                    BpmDeptVo bpmDeptVo = deptMap.get(ownerUser.getDeptId());
                    if (bpmDeptVo != null) {
                        taskVO.getOwnerUser().setDeptName(bpmDeptVo.getName());
                    }
                }
                return taskVO;
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }

    default BpmTaskVo buildTodoTask(Task todoTask, List<Task> childrenTasks,
                                        Map<Integer, BpmTaskVo.OperationButtonSetting> buttonsSetting,
                                        BpmForm form) {
        BpmTaskVo bpmTaskRespVO = BeanUtil.toBean(todoTask, BpmTaskVo.class)
                .setStatus(FlowableUtils.getTaskStatus(todoTask)).setReason(FlowableUtils.getTaskReason(todoTask))
                .setButtonsSetting(buttonsSetting)
                .setChildren(childrenTasks.stream().map(childTask -> BeanUtil.toBean(childTask, BpmTaskVo.class)
                        .setStatus(FlowableUtils.getTaskStatus(childTask))).toList());
        if (form != null) {
            bpmTaskRespVO.setFormId(form.getId()).setFormName(form.getName())
                    .setFormConf(form.getConf()).setFormFields(form.getFields());
        }
        return bpmTaskRespVO;
    }

    default BpmMessageSendWhenTaskCreatedDto convert(ProcessInstance processInstance, BpmUserVo startUser,
                                                     Task task) {
        BpmMessageSendWhenTaskCreatedDto reqDTO = new BpmMessageSendWhenTaskCreatedDto();
        reqDTO.setProcessInstanceId(processInstance.getProcessInstanceId())
                .setProcessInstanceName(processInstance.getName()).setStartUserId(startUser.getId())
                .setStartUserNickname(startUser.getNickname()).setTaskId(task.getId()).setTaskName(task.getName())
                .setAssigneeUserId(NumberUtil.parseLong(task.getAssignee(), null));
        return reqDTO;
    }

    default void buildTaskOwner(BpmTaskVo task, String taskOwner,
                                Map<Long, BpmUserVo> userMap,
                                Map<Long, BpmDeptVo> deptMap) {
        BpmUserVo ownerUser = userMap.get(NumberUtil.parseLong(taskOwner, null));
        if (ownerUser != null) {
            task.setOwnerUser(BeanUtil.toBean(ownerUser, BpmUserSimpleBaseVo.class));
            BpmDeptVo bpmDeptVo = deptMap.get(ownerUser.getDeptId());
            if (bpmDeptVo != null) {
                task.getOwnerUser().setDeptName(bpmDeptVo.getName());
            }
        }
    }

    default void buildTaskChildren(BpmTaskVo task, Map<String, List<Task>> childrenTaskMap,
                                   Map<Long, BpmUserVo> userMap, Map<Long, BpmDeptVo> deptMap) {
        List<Task> childTasks = childrenTaskMap.get(task.getId());
        if (CollUtil.isNotEmpty(childTasks)) {
            task.setChildren(
                    childTasks.stream().map(childTask -> {
                        BpmTaskVo childTaskVO = BeanUtil.toBean(childTask, BpmTaskVo.class);
                        childTaskVO.setStatus(FlowableUtils.getTaskStatus(childTask));
                        buildTaskOwner(childTaskVO, childTask.getOwner(), userMap, deptMap);
                        buildTaskAssignee(childTaskVO, childTask.getAssignee(), userMap, deptMap);
                        return childTaskVO;
                    }).toList()
            );
        }
    }

    default void buildTaskAssignee(BpmTaskVo task, String taskAssignee,
                                   Map<Long, BpmUserVo> userMap,
                                   Map<Long, BpmDeptVo> deptMap) {
        BpmUserVo assignUser = userMap.get(NumberUtil.parseLong(taskAssignee, null));
        if (assignUser != null) {
            task.setAssigneeUser(BeanUtil.toBean(assignUser, BpmUserSimpleBaseVo.class));
            BpmDeptVo bpmDeptVo = deptMap.get(assignUser.getDeptId());
            if (bpmDeptVo != null) {
                task.getAssigneeUser().setDeptName(bpmDeptVo.getName());
            }
        }
    }

    /**
     * 将父任务的属性，拷贝到子任务（加签任务）
     * <p>
     * 为什么不使用 mapstruct 映射？因为 TaskEntityImpl 还有很多其他属性，这里我们只设置我们需要的。
     * 使用 mapstruct 会将里面嵌套的各个属性值都设置进去，会出现意想不到的问题。
     *
     * @param parentTask 父任务
     * @param childTask  加签任务
     */
    default void copyTo(TaskEntityImpl parentTask, TaskEntityImpl childTask) {
        childTask.setName(parentTask.getName());
        childTask.setDescription(parentTask.getDescription());
        childTask.setCategory(parentTask.getCategory());
        childTask.setParentTaskId(parentTask.getId());
        childTask.setProcessDefinitionId(parentTask.getProcessDefinitionId());
        childTask.setProcessInstanceId(parentTask.getProcessInstanceId());
        childTask.setTaskDefinitionKey(parentTask.getTaskDefinitionKey());
        childTask.setTaskDefinitionId(parentTask.getTaskDefinitionId());
        childTask.setPriority(parentTask.getPriority());
        childTask.setCreateTime(new Date());
        childTask.setTenantId(parentTask.getTenantId());
    }

}

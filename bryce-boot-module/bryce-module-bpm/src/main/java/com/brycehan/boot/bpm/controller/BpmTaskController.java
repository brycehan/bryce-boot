package com.brycehan.boot.bpm.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.brycehan.boot.api.system.BpmDeptApi;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.entity.convert.BpmTaskConvert;
import com.brycehan.boot.bpm.entity.dto.*;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmTaskVo;
import com.brycehan.boot.bpm.service.*;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.entity.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流程任务
 * 
 * @since 2025/3/24
 * @author Bryce Han
 */
@Tag(name = "流程任务")
@RestController
@RequestMapping("/bpm/task")
public class BpmTaskController {

    @Resource
    private BpmTaskService taskService;
    @Resource
    private BpmProcessInstanceService processInstanceService;
    @Resource
    private BpmFormService formService;
    @Resource
    private BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;
    @Resource
    private BpmUserApi adminUserApi;
    @Resource
    private BpmDeptApi deptApi;

    @GetMapping("todo-page")
    @Operation(summary = "获取 Todo 待办任务分页")
    @PreAuthorize("@auth.hasAuthority('bpm:task:todo-query')")
    public ResponseResult<PageResult<BpmTaskVo>> getTaskTodoPage(@Validated BpmTaskPageDto pageVO) {
        PageResult<Task> pageResult = taskService.getTaskTodoPage(LoginUserContext.currentUserId(), pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return ResponseResult.ok(PageResult.empty());
        }

        // 拼接数据
        Map<String, ProcessInstance> processInstanceMap = processInstanceService.getProcessInstanceMap(
                pageResult.getList().stream().map(Task::getProcessInstanceId).toList());
        Map<Long, BpmUserVo> userMap = adminUserApi.getUserMap(
                processInstanceMap.values().stream().map(instance -> Long.valueOf(instance.getStartUserId())).toList());
        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(
                pageResult.getList().stream().map(Task::getProcessDefinitionId).toList());
        List<BpmTaskVo> bpmTaskVoList = BpmTaskConvert.INSTANCE.buildTodoTaskPage(pageResult.getList(), processInstanceMap, userMap, processDefinitionInfoMap);
        return ResponseResult.ok(PageResult.of(bpmTaskVoList, pageResult.getTotal()));
    }

    @GetMapping("done-page")
    @Operation(summary = "获取 Done 已办任务分页")
    @PreAuthorize("@auth.hasAuthority('bpm:task:done-query')")
    public ResponseResult<PageResult<BpmTaskVo>> getTaskDonePage(@Validated BpmTaskPageDto pageVO) {
        PageResult<HistoricTaskInstance> pageResult = taskService.getTaskDonePage(LoginUserContext.currentUserId(), pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return ResponseResult.ok(PageResult.empty());
        }

        // 拼接数据
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceService.getHistoricProcessInstanceMap(
                pageResult.getList().stream().map(HistoricTaskInstance::getProcessInstanceId).collect(Collectors.toSet()));
        Map<Long, BpmUserVo> userMap = adminUserApi.getUserMap(
                processInstanceMap.values().stream().map(instance -> Long.valueOf(instance.getStartUserId())).toList());
        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(
                pageResult.getList().stream().map(HistoricTaskInstance::getProcessDefinitionId).toList());
        List<BpmTaskVo> bpmTaskVoList = BpmTaskConvert.INSTANCE.buildTaskPage(pageResult.getList(), processInstanceMap, userMap, null, processDefinitionInfoMap);
        return ResponseResult.ok(PageResult.of(bpmTaskVoList, pageResult.getTotal()));
    }

    /**
     * 获取全部任务的分页
     *
     * @param pageVO 请求参数
     * @return 响应结果
     */
    @PostMapping("manager-page")
    @Operation(summary = "获取全部任务的分页", description = "用于【流程任务】菜单")
    @PreAuthorize("@auth.hasAuthority('bpm:task:manager-query')")
    public ResponseResult<PageResult<BpmTaskVo>> getTaskManagerPage(@RequestBody@Validated BpmTaskPageDto pageVO) {
        PageResult<HistoricTaskInstance> pageResult = taskService.getTaskPage(LoginUserContext.currentUserId(), pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return ResponseResult.ok(PageResult.empty());
        }

        // 拼接数据
        Set<String> processInstanceIds = pageResult.getList().stream().map(HistoricTaskInstance::getProcessInstanceId).collect(Collectors.toSet());
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceService.getHistoricProcessInstanceMap(processInstanceIds);
        // 获得 User 和 Dept Map
        Set<Long> userIds = processInstanceMap.values().stream().map(instance -> Long.valueOf(instance.getStartUserId())).collect(Collectors.toSet());
        userIds.addAll(pageResult.getList().stream().map(task -> NumberUtil.parseLong(task.getOwner(), null)).collect(Collectors.toSet()));
        Map<Long, BpmUserVo> userMap = adminUserApi.getUserMap(userIds.stream().filter(Objects::nonNull).toList());
        Map<Long, BpmDeptVo> deptMap = deptApi.getDeptMap(
                userMap.values().stream().map(BpmUserVo::getDeptId).toList());
        List<String> processDefinitionIds = pageResult.getList().stream().map(HistoricTaskInstance::getProcessDefinitionId).toList();
        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(processDefinitionIds);
        List<BpmTaskVo> bpmTaskVoList = BpmTaskConvert.INSTANCE.buildTaskPage(pageResult.getList(), processInstanceMap, userMap, deptMap, processDefinitionInfoMap);
        return ResponseResult.ok(PageResult.of(bpmTaskVoList, pageResult.getTotal()));
    }

    @GetMapping("/list-by-process-instance-id")
    @Operation(summary = "获得指定流程实例的任务列表", description = "包括完成的、未完成的")
    @Parameter(name = "processInstanceId", description = "流程实例的编号", required = true)
    @PreAuthorize(
            "@auth.hasAnyAuthority('bpm:task:manager-query', 'bpm:process-instance:my-query', 'bpm:process-instance:manager-query', 'bpm:task:todo-query', 'bpm:task:done-query', 'bpm:process-instance:copy-query')"
    )
    public ResponseResult<List<BpmTaskVo>> getTaskListByProcessInstanceId(String processInstanceId) {
        List<HistoricTaskInstance> taskList = taskService.getTaskListByProcessInstanceId(processInstanceId, true);
        if (CollUtil.isEmpty(taskList)) {
            return ResponseResult.ok(Collections.emptyList());
        }

        // 拼接数据
        List<Long> userIds = taskList.stream()
                .flatMap(task -> Stream.of(NumberUtil.parseLong(task.getAssignee(), null), NumberUtil.parseLong(task.getOwner(), null)))
                .filter(Objects::nonNull)
                .toList();

        Map<Long, BpmUserVo> userMap = adminUserApi.getUserMap(userIds);
        Map<Long, BpmDeptVo> deptMap = deptApi.getDeptMap(
                userMap.values().stream().map(BpmUserVo::getDeptId).toList());
        // 获得 Form Map
        List<Long> formIds = taskList.stream().map(task -> NumberUtil.parseLong(task.getFormKey(), null)).toList();
        Map<Long, BpmForm> formMap = formService.getFormMap(formIds);
        List<BpmTaskVo> data = BpmTaskConvert.INSTANCE.buildTaskListByProcessInstanceId(taskList, formMap, userMap, deptMap);
        return ResponseResult.ok(data);
    }

    @PutMapping("/approve")
    @Operation(summary = "通过任务")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> approveTask(@Valid @RequestBody BpmTaskApproveDto reqVO) {
        taskService.approveTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @PutMapping("/reject")
    @Operation(summary = "不通过任务")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> rejectTask(@Valid @RequestBody BpmTaskRejectDto reqVO) {
        taskService.rejectTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @GetMapping("/list-by-return")
    @Operation(summary = "获取所有可退回的节点", description = "用于【流程详情】的【退回】按钮")
    @Parameter(name = "taskId", description = "当前任务ID", required = true)
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<List<BpmTaskVo>> getTaskListByReturn(@RequestParam("id") String id) {
        List<UserTask> userTaskList = taskService.getUserTaskListByReturn(id);
        List<BpmTaskVo> list = userTaskList.stream().map(userTask -> new BpmTaskVo().setName(userTask.getName()).setTaskDefinitionKey(userTask.getId())).toList();
        return ResponseResult.ok(list);
    }

    @PutMapping("/return")
    @Operation(summary = "退回任务", description = "用于【流程详情】的【退回】按钮")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> returnTask(@Valid @RequestBody BpmTaskReturnDto reqVO) {
        taskService.returnTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @PutMapping("/delegate")
    @Operation(summary = "委派任务", description = "用于【流程详情】的【委派】按钮")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> delegateTask(@Valid @RequestBody BpmTaskDelegateDto reqVO) {
        taskService.delegateTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @PutMapping("/transfer")
    @Operation(summary = "转派任务", description = "用于【流程详情】的【转派】按钮")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> transferTask(@Valid @RequestBody BpmTaskTransferDto reqVO) {
        taskService.transferTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @PutMapping("/create-sign")
    @Operation(summary = "加签", description = "before 前加签，after 后加签")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> createSignTask(@Valid @RequestBody BpmTaskSignDto reqVO) {
        taskService.createSignTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @DeleteMapping("/delete-sign")
    @Operation(summary = "减签")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> deleteSignTask(@Valid @RequestBody BpmTaskSignDeleteDto reqVO) {
        taskService.deleteSignTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @PutMapping("/copy")
    @Operation(summary = "抄送任务")
    @PreAuthorize("@auth.hasAuthority('bpm:task:update')")
    public ResponseResult<Boolean> copyTask(@Valid @RequestBody BpmTaskCopyDto reqVO) {
        taskService.copyTask(LoginUserContext.currentUserId(), reqVO);
        return ResponseResult.ok(true);
    }

    @GetMapping("/list-by-parent-task-id")
    @Operation(summary = "获得指定父级任务的子任务列表") // 目前用于，减签的时候，获得子任务列表
    @Parameter(name = "parentTaskId", description = "父级任务编号", required = true)
    @PreAuthorize("@auth.hasAuthority('bpm:task:query')")
    public ResponseResult<List<BpmTaskVo>> getTaskListByParentTaskId(@RequestParam("parentTaskId") String parentTaskId) {
        List<Task> taskList = taskService.getTaskListByParentTaskId(parentTaskId);
        if (CollUtil.isEmpty(taskList)) {
            return ResponseResult.ok(Collections.emptyList());
        }
        // 拼接数据
        List<Long> userIds = taskList.stream()
                .flatMap(task -> Stream.of(NumberUtil.parseLong(task.getAssignee(), null), NumberUtil.parseLong(task.getOwner(), null)))
                .filter(Objects::nonNull)
                .toList();
        Map<Long, BpmUserVo> userMap = adminUserApi.getUserMap(userIds);
        List<Long> deptIds = userMap.values().stream().map(BpmUserVo::getDeptId).toList();
        Map<Long, BpmDeptVo> deptMap = deptApi.getDeptMap(deptIds);
        List<BpmTaskVo> bpmTaskVoList = BpmTaskConvert.INSTANCE.buildTaskListByParentTaskId(taskList, userMap, deptMap);
        return ResponseResult.ok(bpmTaskVoList);
    }

}

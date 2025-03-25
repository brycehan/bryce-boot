package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.bpm.entity.dto.BpmApprovalDetailDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCancelDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstancePageDto;
import com.brycehan.boot.bpm.entity.vo.BpmApprovalDetailVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessInstanceBpmnModelViewVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessInstanceVo;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.entity.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程实例API
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Tag(name = "流程实例", description = "bpmProcessInstance")
@RequestMapping("/bpm/process-instance")
@RestController
@RequiredArgsConstructor
public class BpmProcessInstanceController {

    private final BpmProcessInstanceService bpmProcessInstanceService;

    /**
     * 新建流程实例
     *
     * @param bpmProcessInstanceDto 流程实例Dto
     * @return 响应结果
     */
    @Operation(summary = "新建流程实例")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:save')")
    @PostMapping
    public ResponseResult<String> save(@Valid @RequestBody BpmProcessInstanceDto bpmProcessInstanceDto) {
        String processInstance = bpmProcessInstanceService.createProcessInstance(LoginUserContext.currentUserId(), bpmProcessInstanceDto);
        return ResponseResult.ok(processInstance);
    }

    /**
     * 查询流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程实例详情")
    @PreAuthorize("@auth.hasAuthority('bpm:process-definition:info')")
    @GetMapping(path = "/{processInstanceId}")
    public ResponseResult<BpmProcessInstanceVo> get(@PathVariable String processInstanceId) {
        BpmProcessInstanceVo bpmProcessInstanceVo = bpmProcessInstanceService.getById(processInstanceId);
        return ResponseResult.ok(bpmProcessInstanceVo);
    }

    /**
     * 流程实例分页查询
     *
     * @param bpmProcessInstancePageDto 查询条件
     * @return 流程实例分页列表
     */
    @Operation(summary = "流程实例分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:manager-page')")
    @PostMapping(path = "/manager-page")
    public ResponseResult<PageResult<BpmProcessInstanceVo>> managerPage(@Validated @RequestBody BpmProcessInstancePageDto bpmProcessInstancePageDto) {
        PageResult<BpmProcessInstanceVo> managerPage = bpmProcessInstanceService.managerPage(bpmProcessInstancePageDto);
        return ResponseResult.ok(managerPage);
    }

    /**
     * 流程实例分页查询
     *
     * @param bpmProcessInstancePageDto 查询条件
     * @return 流程实例分页列表
     */
    @Operation(summary = "流程实例分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:my-page')")
    @PostMapping(path = "/my-page")
    public ResponseResult<PageResult<BpmProcessInstanceVo>> myPage(@Validated @RequestBody BpmProcessInstancePageDto bpmProcessInstancePageDto) {
        PageResult<BpmProcessInstanceVo> managerPage = bpmProcessInstanceService.myPage(bpmProcessInstancePageDto);
        return ResponseResult.ok(managerPage);
    }

    /**
     * 用户取消流程实例
     *
     * @param bpmProcessInstanceCancelDto 取消流程实例参数
     * @return 响应结果
     */
    @DeleteMapping("/cancel-by-start-user")
    @Operation(summary = "用户取消流程实例", description = "取消发起的流程")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:cancel')")
    public ResponseResult<Boolean> cancelProcessInstanceByStartUser(@Validated @RequestBody BpmProcessInstanceCancelDto bpmProcessInstanceCancelDto) {
        bpmProcessInstanceService.cancelProcessInstanceByStartUser(LoginUserContext.currentUserId(), bpmProcessInstanceCancelDto);
        return ResponseResult.ok();
    }

    /**
     * 管理员取消流程实例
     *
     * @param cancelReqVO 取消流程实例参数
     * @return 响应结果
     */
    @DeleteMapping("/cancel-by-admin")
    @Operation(summary = "管理员取消流程实例", description = "管理员撤回流程")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:cancel-by-admin')")
    public ResponseResult<Boolean> cancelProcessInstanceByManager(@Validated @RequestBody BpmProcessInstanceCancelDto cancelReqVO) {
        bpmProcessInstanceService.cancelProcessInstanceByAdmin(LoginUserContext.currentUserId(), cancelReqVO);
        return ResponseResult.ok();
    }

    /**
     * 获得审批详情
     *
     * @param bpmApprovalDetailDto 审批详情参数
     * @return 审批详情
     */
    @GetMapping("/get-approval-detail")
    @Operation(summary = "获得审批详情")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:query')")
    public ResponseResult<BpmApprovalDetailVo> getApprovalDetail(@Validated BpmApprovalDetailDto bpmApprovalDetailDto) {
        BpmApprovalDetailVo bpmApprovalDetailVo = bpmProcessInstanceService.getApprovalDetail(LoginUserContext.currentUserId(), bpmApprovalDetailDto);
        return ResponseResult.ok(bpmApprovalDetailVo);
    }

    /**
     * 获取流程实例的 BPMN 模型视图
     *
     * @param id 流程实例的编号
     * @return 响应结果
     */
    @GetMapping("/get-bpmn-model-view")
    @Operation(summary = "获取流程实例的 BPMN 模型视图", description = "在【流程详细】界面中，进行调用")
    @Parameter(name = "id", description = "流程实例的编号", required = true)
    public ResponseResult<BpmProcessInstanceBpmnModelViewVo> getProcessInstanceBpmnModelView(@RequestParam(value = "id") String id) {
        BpmProcessInstanceBpmnModelViewVo bpmProcessInstanceBpmnModelViewVo = bpmProcessInstanceService.getProcessInstanceBpmnModelView(id);
        return ResponseResult.ok(bpmProcessInstanceBpmnModelViewVo);
    }
}
package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.bpm.entity.dto.BpmApprovalDetailDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionPageDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceDto;
import com.brycehan.boot.bpm.entity.vo.BpmApprovalDetailVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.entity.PageResult;
import io.swagger.v3.oas.annotations.Operation;
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
     * 查询流程定义详情
     *
     * @param id 流程定义ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程定义信息详情")
    @PreAuthorize("@auth.hasAuthority('bpm:process-definition:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmProcessDefinitionVo> get(@PathVariable String id) {
//        HistoricProcessInstance processInstance = processInstanceService.getHistoricProcessInstance(id);
        return ResponseResult.ok();
    }

    /**
     * 流程定义分页查询
     *
     * @param bpmProcessDefinitionPageDto 查询条件
     * @return 流程定义分页列表
     */
    @Operation(summary = "流程定义分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:process-definition:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmProcessDefinitionVo>> page(@Validated @RequestBody BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto) {
//        PageResult<BpmProcessDefinitionVo> page = bpmProcessDefinitionService.page(bpmProcessDefinitionPageDto);
        return ResponseResult.ok();
    }

    @GetMapping("/get-approval-detail")
    @Operation(summary = "获得审批详情")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:query')")
    public ResponseResult<BpmApprovalDetailVo> getApprovalDetail(@Validated BpmApprovalDetailDto bpmApprovalDetailDto) {
        BpmApprovalDetailVo bpmApprovalDetailVo = bpmProcessInstanceService.getApprovalDetail(LoginUserContext.currentUserId(), bpmApprovalDetailDto);
        return ResponseResult.ok(bpmApprovalDetailVo);
    }
}
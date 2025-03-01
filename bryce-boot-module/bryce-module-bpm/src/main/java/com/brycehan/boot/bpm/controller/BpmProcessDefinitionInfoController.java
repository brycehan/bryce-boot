package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.bpm.entity.convert.BpmProcessDefinitionInfoConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程定义信息API
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Tag(name = "流程定义信息", description = "bpmProcessDefinitionInfo")
@RequestMapping("/bpm/processDefinitionInfo")
@RestController
@RequiredArgsConstructor
public class BpmProcessDefinitionInfoController {

    private final BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;

    /**
     * 保存流程定义信息
     *
     * @param bpmProcessDefinitionInfoDto 流程定义信息Dto
     * @return 响应结果
     */
    @Operation(summary = "保存流程定义信息")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:processDefinitionInfo:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto) {
        bpmProcessDefinitionInfoService.save(bpmProcessDefinitionInfoDto);
        return ResponseResult.ok();
    }

    /**
     * 更新流程定义信息
     *
     * @param bpmProcessDefinitionInfoDto 流程定义信息Dto
     * @return 响应结果
     */
    @Operation(summary = "更新流程定义信息")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:processDefinitionInfo:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto) {
        bpmProcessDefinitionInfoService.update(bpmProcessDefinitionInfoDto);
        return ResponseResult.ok();
    }

    /**
     * 删除流程定义信息
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除流程定义信息")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:processDefinitionInfo:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        bpmProcessDefinitionInfoService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询流程定义信息详情
     *
     * @param id 流程定义信息ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程定义信息详情")
    @PreAuthorize("@auth.hasAuthority('bpm:processDefinitionInfo:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmProcessDefinitionInfoVo> get(@Parameter(description = "流程定义信息ID", required = true) @PathVariable Long id) {
        BpmProcessDefinitionInfo bpmProcessDefinitionInfo = bpmProcessDefinitionInfoService.getById(id);
        return ResponseResult.ok(BpmProcessDefinitionInfoConvert.INSTANCE.convert(bpmProcessDefinitionInfo));
    }

    /**
     * 流程定义信息分页查询
     *
     * @param bpmProcessDefinitionInfoPageDto 查询条件
     * @return 流程定义信息分页列表
     */
    @Operation(summary = "流程定义信息分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:processDefinitionInfo:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmProcessDefinitionInfoVo>> page(@Validated @RequestBody BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto) {
        PageResult<BpmProcessDefinitionInfoVo> page = bpmProcessDefinitionInfoService.page(bpmProcessDefinitionInfoPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 流程定义信息导出数据
     *
     * @param bpmProcessDefinitionInfoPageDto 查询条件
     */
    @Operation(summary = "流程定义信息导出")
    @PreAuthorize("@auth.hasAuthority('bpm:processDefinitionInfo:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto) {
        bpmProcessDefinitionInfoService.export(bpmProcessDefinitionInfoPageDto);
    }

}
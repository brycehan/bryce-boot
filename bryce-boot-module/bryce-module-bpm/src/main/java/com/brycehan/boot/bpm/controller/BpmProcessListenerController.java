package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.bpm.entity.convert.BpmProcessListenerConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessListener;
import com.brycehan.boot.bpm.service.BpmProcessListenerService;
import com.brycehan.boot.bpm.entity.vo.BpmProcessListenerVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程监听器API
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Tag(name = "流程监听器", description = "bpmProcessListener")
@RequestMapping("/bpm/process-listener")
@RestController
@RequiredArgsConstructor
public class BpmProcessListenerController {

    private final BpmProcessListenerService bpmProcessListenerService;

    /**
     * 保存流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     * @return 响应结果
     */
    @Operation(summary = "保存流程监听器")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:process-listener:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmProcessListenerDto bpmProcessListenerDto) {
        bpmProcessListenerService.save(bpmProcessListenerDto);
        return ResponseResult.ok();
    }

    /**
     * 更新流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     * @return 响应结果
     */
    @Operation(summary = "更新流程监听器")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:process-listener:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmProcessListenerDto bpmProcessListenerDto) {
        bpmProcessListenerService.update(bpmProcessListenerDto);
        return ResponseResult.ok();
    }

    /**
     * 删除流程监听器
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除流程监听器")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:process-listener:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        bpmProcessListenerService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询流程监听器详情
     *
     * @param id 流程监听器ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程监听器详情")
    @PreAuthorize("@auth.hasAuthority('bpm:process-listener:query')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmProcessListenerVo> get(@Parameter(description = "流程监听器ID", required = true) @PathVariable Long id) {
        BpmProcessListener bpmProcessListener = bpmProcessListenerService.getById(id);
        return ResponseResult.ok(BpmProcessListenerConvert.INSTANCE.convert(bpmProcessListener));
    }

    /**
     * 流程监听器分页查询
     *
     * @param bpmProcessListenerPageDto 查询条件
     * @return 流程监听器分页列表
     */
    @Operation(summary = "流程监听器分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:process-listener:query')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmProcessListenerVo>> page(@Validated @RequestBody BpmProcessListenerPageDto bpmProcessListenerPageDto) {
        PageResult<BpmProcessListenerVo> page = bpmProcessListenerService.page(bpmProcessListenerPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 流程监听器导出数据
     *
     * @param bpmProcessListenerPageDto 查询条件
     */
    @Operation(summary = "流程监听器导出")
    @PreAuthorize("@auth.hasAuthority('bpm:process-listener:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody BpmProcessListenerPageDto bpmProcessListenerPageDto) {
        bpmProcessListenerService.export(bpmProcessListenerPageDto);
    }

}
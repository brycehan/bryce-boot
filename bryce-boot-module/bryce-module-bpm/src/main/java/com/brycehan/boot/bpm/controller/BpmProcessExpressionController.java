package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.bpm.entity.convert.BpmProcessExpressionConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessExpression;
import com.brycehan.boot.bpm.service.BpmProcessExpressionService;
import com.brycehan.boot.bpm.entity.vo.BpmProcessExpressionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程表达式API
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Tag(name = "流程表达式", description = "bpmProcessExpression")
@RequestMapping("/bpm/process-expression")
@RestController
@RequiredArgsConstructor
public class BpmProcessExpressionController {

    private final BpmProcessExpressionService bpmProcessExpressionService;

    /**
     * 保存流程表达式
     *
     * @param bpmProcessExpressionDto 流程表达式Dto
     * @return 响应结果
     */
    @Operation(summary = "保存流程表达式")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:process-expression:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmProcessExpressionDto bpmProcessExpressionDto) {
        bpmProcessExpressionService.save(bpmProcessExpressionDto);
        return ResponseResult.ok();
    }

    /**
     * 更新流程表达式
     *
     * @param bpmProcessExpressionDto 流程表达式Dto
     * @return 响应结果
     */
    @Operation(summary = "更新流程表达式")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:process-expression:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmProcessExpressionDto bpmProcessExpressionDto) {
        bpmProcessExpressionService.update(bpmProcessExpressionDto);
        return ResponseResult.ok();
    }

    /**
     * 删除流程表达式
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除流程表达式")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:process-expression:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        bpmProcessExpressionService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询流程表达式详情
     *
     * @param id 流程表达式ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程表达式详情")
    @PreAuthorize("@auth.hasAuthority('bpm:process-expression:query')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmProcessExpressionVo> get(@Parameter(description = "流程表达式ID", required = true) @PathVariable Long id) {
        BpmProcessExpression bpmProcessExpression = bpmProcessExpressionService.getById(id);
        return ResponseResult.ok(BpmProcessExpressionConvert.INSTANCE.convert(bpmProcessExpression));
    }

    /**
     * 流程表达式分页查询
     *
     * @param bpmProcessExpressionPageDto 查询条件
     * @return 流程表达式分页列表
     */
    @Operation(summary = "流程表达式分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:process-expression:query')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmProcessExpressionVo>> page(@Validated @RequestBody BpmProcessExpressionPageDto bpmProcessExpressionPageDto) {
        PageResult<BpmProcessExpressionVo> page = bpmProcessExpressionService.page(bpmProcessExpressionPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 流程表达式导出数据
     *
     * @param bpmProcessExpressionPageDto 查询条件
     */
    @Operation(summary = "流程表达式导出")
    @PreAuthorize("@auth.hasAuthority('bpm:process-expression:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody BpmProcessExpressionPageDto bpmProcessExpressionPageDto) {
        bpmProcessExpressionService.export(bpmProcessExpressionPageDto);
    }

}
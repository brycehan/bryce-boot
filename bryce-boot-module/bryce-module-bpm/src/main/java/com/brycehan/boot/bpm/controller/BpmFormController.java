package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.bpm.entity.convert.BpmFormConvert;
import com.brycehan.boot.bpm.entity.dto.BpmFormDto;
import com.brycehan.boot.bpm.entity.dto.BpmFormPageDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.service.BpmFormService;
import com.brycehan.boot.bpm.entity.vo.BpmFormVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表单定义API
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Tag(name = "表单定义", description = "bpmForm")
@RequestMapping("/bpm/form")
@RestController
@RequiredArgsConstructor
public class BpmFormController {

    private final BpmFormService bpmFormService;

    /**
     * 保存表单定义
     *
     * @param bpmFormDto 表单定义Dto
     * @return 响应结果
     */
    @Operation(summary = "保存表单定义")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:form:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmFormDto bpmFormDto) {
        bpmFormService.save(bpmFormDto);
        return ResponseResult.ok();
    }

    /**
     * 更新表单定义
     *
     * @param bpmFormDto 表单定义Dto
     * @return 响应结果
     */
    @Operation(summary = "更新表单定义")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:form:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmFormDto bpmFormDto) {
        bpmFormService.update(bpmFormDto);
        return ResponseResult.ok();
    }

    /**
     * 删除表单定义
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除表单定义")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:form:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        bpmFormService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询表单定义详情
     *
     * @param id 表单定义ID
     * @return 响应结果
     */
    @Operation(summary = "查询表单定义详情")
    @PreAuthorize("@auth.hasAuthority('bpm:form:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmFormVo> get(@Parameter(description = "表单定义ID", required = true) @PathVariable Long id) {
        BpmForm bpmForm = bpmFormService.getById(id);
        return ResponseResult.ok(BpmFormConvert.INSTANCE.convert(bpmForm));
    }

    /**
     * 表单定义分页查询
     *
     * @param bpmFormPageDto 查询条件
     * @return 表单定义分页列表
     */
    @Operation(summary = "表单定义分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:form:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmFormVo>> page(@Validated @RequestBody BpmFormPageDto bpmFormPageDto) {
        PageResult<BpmFormVo> page = bpmFormService.page(bpmFormPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 表单定义列表查询
     *
     * @param bpmFormPageDto 查询条件
     * @return 表单定义分页列表
     */
    @Operation(summary = "表单定义列表查询")
    @PostMapping(path = "/list")
    public ResponseResult<List<BpmFormVo>> list(@Validated @RequestBody BpmFormPageDto bpmFormPageDto) {
        List<BpmFormVo> list = bpmFormService.list(bpmFormPageDto);
        return ResponseResult.ok(list);
    }

    /**
     * 表单定义导出数据
     *
     * @param bpmFormPageDto 查询条件
     */
    @Operation(summary = "表单定义导出")
    @PreAuthorize("@auth.hasAuthority('bpm:form:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody BpmFormPageDto bpmFormPageDto) {
        bpmFormService.export(bpmFormPageDto);
    }

}
package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.convert.SysDeptConvert;
import com.brycehan.boot.system.entity.dto.SysDeptDto;
import com.brycehan.boot.system.entity.po.SysDept;
import com.brycehan.boot.system.entity.vo.SysDeptSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDeptVo;
import com.brycehan.boot.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统部门 API
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Tag(name = "系统部门")
@RequestMapping("/system/dept")
@RestController
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 保存系统部门
     *
     * @param sysDeptDto 系统部门Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统部门")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:dept:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        sysDeptService.save(sysDeptDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统部门
     *
     * @param sysDeptDto 系统部门Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统部门")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:dept:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        sysDeptService.update(sysDeptDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统部门
     *
     * @param idsDto 系统部门删除Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统部门")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:dept:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysDeptService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统部门详情
     *
     * @param id 系统部门ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统部门详情")
    @PreAuthorize("@auth.hasAuthority('system:dept:query')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysDeptVo> get(@Parameter(description = "系统部门ID", required = true) @PathVariable Long id) {
        sysDeptService.checkDeptDataScope(id);
        SysDept sysDept = sysDeptService.getById(id);
        return ResponseResult.ok(SysDeptConvert.INSTANCE.convert(sysDept));
    }

    /**
     * 系统部门列表查询
     *
     * @param sysDeptDto 查询条件
     * @return 系统部门列表
     */
    @Operation(summary = "列表查询")
    @PreAuthorize("@auth.hasAuthority('system:dept:list')")
    @PostMapping(path = "/list")
    public ResponseResult<List<SysDeptVo>> list(@Validated @RequestBody SysDeptDto sysDeptDto) {
        List<SysDeptVo> list = sysDeptService.list(sysDeptDto);
        return ResponseResult.ok(list);
    }

    /**
     * 系统部门列表查询，用于前端 select 下拉框
     *
     * @param sysDeptDto 查询条件
     * @return 系统部门列表
     */
    @Operation(summary = "列表查询")
    @GetMapping(path = "/simple-list")
    public ResponseResult<List<SysDeptSimpleVo>> simpleList(SysDeptDto sysDeptDto) {
        List<SysDeptSimpleVo> list = sysDeptService.simpleList(sysDeptDto);
        return ResponseResult.ok(list);
    }

}
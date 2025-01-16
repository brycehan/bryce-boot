package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.convert.SysDictTypeConvert;
import com.brycehan.boot.system.entity.dto.SysDictTypeCodeDto;
import com.brycehan.boot.system.entity.dto.SysDictTypeDto;
import com.brycehan.boot.system.entity.dto.SysDictTypePageDto;
import com.brycehan.boot.system.entity.po.SysDictType;
import com.brycehan.boot.system.entity.vo.SysDictTypeVo;
import com.brycehan.boot.system.entity.vo.SysDictVo;
import com.brycehan.boot.system.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典类型API
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Tag(name = "系统字典类型")
@RequestMapping("/system/dictType")
@RestController
@RequiredArgsConstructor
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    /**
     * 保存系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统字典类型")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:dictType:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysDictTypeDto sysDictTypeDto) {
        sysDictTypeService.save(sysDictTypeDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统字典类型")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:dictType:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysDictTypeDto sysDictTypeDto) {
        this.sysDictTypeService.update(sysDictTypeDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统字典类型
     *
     * @param idsDto Id列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统字典类型")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:dictType:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysDictTypeService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统字典类型详情
     *
     * @param id 系统字典类型ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统字典类型详情")
    @PreAuthorize("@auth.hasAuthority('system:dictType:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysDictTypeVo> get(@Parameter(description = "系统字典类型ID", required = true) @PathVariable Long id) {
        SysDictType sysDictType = this.sysDictTypeService.getById(id);
        return ResponseResult.ok(SysDictTypeConvert.INSTANCE.convert(sysDictType));
    }

    /**
     * 系统字典类型分页查询
     *
     * @param sysDictTypePageDto 查询条件
     * @return 系统字典类型分页列表
     */
    @Operation(summary = "系统字典类型分页查询")
    @PreAuthorize("@auth.hasAuthority('system:dictType:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysDictTypeVo>> page(@Validated @RequestBody SysDictTypePageDto sysDictTypePageDto) {
        PageResult<SysDictTypeVo> page = this.sysDictTypeService.page(sysDictTypePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统字典类型导出数据
     *
     * @param sysDictTypePageDto 查询条件
     */
    @Operation(summary = "系统字典类型导出")
    @PreAuthorize("@auth.hasAuthority('system:dictType:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysDictTypePageDto sysDictTypePageDto) {
        this.sysDictTypeService.export(sysDictTypePageDto);
    }

    /**
     * 全部字典数据
     *
     * @return 全部字典数据列表
     */
    @Operation(summary = "全部字典数据")
    @GetMapping(path = "/dictList")
    public ResponseResult<List<SysDictVo>> dictList() {
        List<SysDictVo> dictVoList = this.sysDictTypeService.dictList();
        return ResponseResult.ok(dictVoList);
    }

    /**
     * 校验字典类型编码是否唯一
     *
     * @param sysDictTypeCodeDto 字典类型编码Dto
     * @return 响应结果，是否唯一
     */
    @Operation(summary = "校验字典类型编码是否唯一（true：唯一，false：不唯一）")
    @GetMapping(path = "/checkDictTypeCodeUnique")
    public ResponseResult<Boolean> checkDictTypeCodeUnique(@Validated SysDictTypeCodeDto sysDictTypeCodeDto) {
        boolean checked = this.sysDictTypeService.checkDictTypeCodeUnique(sysDictTypeCodeDto);
        return ResponseResult.ok(checked);
    }

}
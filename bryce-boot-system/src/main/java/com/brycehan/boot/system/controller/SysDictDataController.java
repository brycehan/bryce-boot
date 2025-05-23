package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.convert.SysDictDataConvert;
import com.brycehan.boot.system.entity.dto.SysDictDataDto;
import com.brycehan.boot.system.entity.dto.SysDictDataPageDto;
import com.brycehan.boot.system.entity.po.SysDictData;
import com.brycehan.boot.system.entity.vo.SysDictDataSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictDataVo;
import com.brycehan.boot.system.service.SysDictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统字典数据API
 *
 * @author Bryce Han
 * @since 2023/09/08
 */
@Tag(name = "系统字典数据")
@RequestMapping("/system/dictData")
@RestController
@RequiredArgsConstructor
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    /**
     * 保存系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统字典数据")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:dictData:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysDictDataDto sysDictDataDto) {
        sysDictDataService.save(sysDictDataDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统字典数据")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:dictData:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysDictDataDto sysDictDataDto) {
        sysDictDataService.update(sysDictDataDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统字典数据
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统字典数据")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:dictData:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysDictDataService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统字典数据详情
     *
     * @param id 系统字典数据ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统字典数据详情")
    @PreAuthorize("@auth.hasAuthority('system:dictData:query')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysDictDataVo> get(@Parameter(description = "系统字典数据ID", required = true) @PathVariable Long id) {
        SysDictData sysDictData = sysDictDataService.getById(id);
        return ResponseResult.ok(SysDictDataConvert.INSTANCE.convert(sysDictData));
    }

    /**
     * 系统字典数据分页查询
     *
     * @param sysDictDataPageDto 查询条件
     * @return 系统字典数据分页列表
     */
    @Operation(summary = "系统字典数据分页查询")
    @PreAuthorize("@auth.hasAuthority('system:dictData:query')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysDictDataVo>> page(@Validated @RequestBody SysDictDataPageDto sysDictDataPageDto) {
        PageResult<SysDictDataVo> page = sysDictDataService.page(sysDictDataPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 全部字典数据
     *
     * @return 全部字典数据列表
     */
    @Operation(summary = "全部字典数据")
    @GetMapping(path = "/dictMap")
    public ResponseResult<Map<String, List<SysDictDataSimpleVo>>> dictMap() {
        Map<String, List<SysDictDataSimpleVo>> dictMap = sysDictDataService.dictMap(StatusType.ENABLE);
        return ResponseResult.ok(dictMap);
    }

}
package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.convert.SysParamConvert;
import com.brycehan.boot.system.dto.SysParamDto;
import com.brycehan.boot.system.dto.SysParamPageDto;
import com.brycehan.boot.system.entity.SysParam;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.vo.SysParamVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数API
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Tag(name = "系统参数")
@RequestMapping("/system/param")
@RestController
@RequiredArgsConstructor
public class SysParamController {

    private final SysParamService sysParamService;

    /**
     * 保存系统参数
     *
     * @param sysParamDto 系统参数Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统参数")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('system:param:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysParamDto sysParamDto) {
        this.sysParamService.save(sysParamDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统参数")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:param:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysParamDto sysParamDto) {
        this.sysParamService.update(sysParamDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统参数
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统参数")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:param:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysParamService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统参数详情
     *
     * @param id 系统参数ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统参数详情")
    @PreAuthorize("hasAuthority('system:param:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysParamVo> get(@Parameter(description = "系统参数ID", required = true) @PathVariable Long id) {
        SysParam sysParam = this.sysParamService.getById(id);
        return ResponseResult.ok(SysParamConvert.INSTANCE.convert(sysParam));
    }

    /**
     * 分页查询
     *
     * @param sysParamPageDto 查询条件
     * @return 系统参数分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:param:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysParamVo>> page(@Validated @RequestBody SysParamPageDto sysParamPageDto) {
        PageResult<SysParamVo> page = this.sysParamService.page(sysParamPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统参数导出数据
     *
     * @param sysParamPageDto 查询条件
     */
    @Operation(summary = "系统参数导出")
    @PreAuthorize("hasAuthority('system:param:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysParamPageDto sysParamPageDto) {
        this.sysParamService.export(sysParamPageDto);
    }

}
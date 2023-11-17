package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.convert.SysTenantConvert;
import com.brycehan.boot.system.dto.SysTenantDto;
import com.brycehan.boot.system.dto.SysTenantPageDto;
import com.brycehan.boot.system.entity.SysTenant;
import com.brycehan.boot.system.service.SysTenantService;
import com.brycehan.boot.system.vo.SysTenantVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统租户API
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Tag(name = "系统租户", description = "sysTenant")
@RequestMapping("/system/tenant")
@RestController
@RequiredArgsConstructor
public class SysTenantController {

    private final SysTenantService sysTenantService;

    /**
     * 保存系统租户
     *
     * @param sysTenantDto 系统租户Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统租户")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('system:tenant:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysTenantDto sysTenantDto) {
        this.sysTenantService.save(sysTenantDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统租户
     *
     * @param sysTenantDto 系统租户Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统租户")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:tenant:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysTenantDto sysTenantDto) {
        this.sysTenantService.update(sysTenantDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统租户
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统租户")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:tenant:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysTenantService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统租户详情
     *
     * @param id 系统租户ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统租户详情")
    @PreAuthorize("hasAuthority('system:tenant:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysTenantVo> get(@Parameter(description = "系统租户ID", required = true) @PathVariable Long id) {
        SysTenant sysTenant = this.sysTenantService.getById(id);
        return ResponseResult.ok(SysTenantConvert.INSTANCE.convert(sysTenant));
    }

    /**
     * 分页查询
     *
     * @param sysTenantPageDto 查询条件
     * @return 系统租户分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:tenant:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysTenantVo>> page(@Validated @RequestBody SysTenantPageDto sysTenantPageDto) {
        PageResult<SysTenantVo> page = this.sysTenantService.page(sysTenantPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统租户导出数据
     *
     * @param sysTenantPageDto 查询条件
     */
    @Operation(summary = "系统租户导出")
    @PreAuthorize("hasAuthority('system:tenant:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysTenantPageDto sysTenantPageDto) {
        this.sysTenantService.export(sysTenantPageDto);
    }

}
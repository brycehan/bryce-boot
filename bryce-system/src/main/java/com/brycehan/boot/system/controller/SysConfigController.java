package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.annotation.Log;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.enums.BusinessType;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.brycehan.boot.system.dto.SysConfigPageDto;
import com.brycehan.boot.system.entity.SysConfig;
import com.brycehan.boot.system.service.SysConfigService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 系统配置控制器
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Tag(name = "sysConfig", description = "系统配置API")
@RequestMapping("/system/config")
@RestController
public class SysConfigController {

    private final SysConfigService sysConfigService;

    public SysConfigController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    /**
     * 保存系统配置
     *
     * @param sysConfig 系统配置
     * @return 响应结果
     */
    @Operation(summary = "保存系统配置")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseResult<Void> save(@Parameter(description = "系统配置", required = true) @Validated(value = AddGroup.class) @RequestBody SysConfig sysConfig) {
        sysConfig.setId(IdGenerator.generate());
        this.sysConfigService.save(sysConfig);
        return ResponseResult.ok();
    }

    /**
     * 更新系统配置
     *
     * @param sysConfig 系统配置
     * @return 响应结果
     */
    @Operation(summary = "更新系统配置")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统配置实体", required = true) @Validated(value = UpdateGroup.class) @RequestBody SysConfig sysConfig) {
        this.sysConfigService.updateById(sysConfig);
        return ResponseResult.ok();
    }

    /**
     * 删除系统配置
     *
     * @param id 系统配置ID
     * @return 响应结果
     */
    @Operation(summary = "删除系统配置")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping(path = "/{id}")
    public ResponseResult<Void> deleteById(@Parameter(description = "系统配置ID", required = true) @PathVariable String id) {
        this.sysConfigService.removeById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据系统配置 ID 查询系统配置信息
     *
     * @param id 系统配置
     * @return 响应结果
     */
    @Operation(summary = "根据系统配置ID查询系统配置详情")
    @GetMapping(path = "/item/{id}")
    public ResponseResult<SysConfig> getById(@Parameter(description = "系统配置ID", required = true) @PathVariable String id) {
        SysConfig sysConfig = this.sysConfigService.getById(id);
        return ResponseResult.ok(sysConfig);
    }

    /**
     * 分页查询
     *
     * @param sysConfigPageDto 查询条件
     * @return 分页系统配置
     */
    @Operation(summary = "分页查询")
    @GetMapping(path = "/page")
    public ResponseResult<PageInfo<SysConfig>> page(@Parameter(description = "查询信息", required = true) @RequestBody SysConfigPageDto sysConfigPageDto) {
        PageInfo<SysConfig> pageInfo = this.sysConfigService.page(sysConfigPageDto);
        return ResponseResult.ok(pageInfo);
    }

}


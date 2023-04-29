package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.brycehan.boot.system.dto.SysLoginInfoPageDto;
import com.brycehan.boot.system.entity.SysLoginInfo;
import com.brycehan.boot.system.service.SysLoginInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 系统登录信息控制器
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Tag(name = "sysLoginInfo", description = "系统登录信息API")
@RequestMapping("/system/sysLoginInfo")
@RestController
public class SysLoginInfoController {

    private final SysLoginInfoService sysLoginInfoService;

    public SysLoginInfoController(SysLoginInfoService sysLoginInfoService) {
        this.sysLoginInfoService = sysLoginInfoService;
    }

    /**
     * 保存系统登录信息
     *
     * @param sysLoginInfo 系统登录信息
     * @return 响应结果
     */
    @Operation(summary = "保存系统登录信息")
    @PostMapping
    public ResponseResult<Void> save(@Parameter(description = "系统登录信息", required = true) @Validated(value = AddGroup.class) @RequestBody SysLoginInfo sysLoginInfo) {
        sysLoginInfo.setId(IdGenerator.generate());
        this.sysLoginInfoService.save(sysLoginInfo);
        return ResponseResult.ok();
    }

    /**
     * 删除系统登录信息
     *
     * @param id 系统登录信息ID
     * @return 响应结果
     */
    @Operation(summary = "删除系统登录信息")
    @DeleteMapping(path = "/{id}")
    public ResponseResult<Void> deleteById(@Parameter(description = "系统登录信息ID", required = true) @PathVariable String id) {
        this.sysLoginInfoService.removeById(id);
        return ResponseResult.ok();
    }

    /**
     * 更新系统登录信息
     *
     * @param sysLoginInfo 系统登录信息
     * @return 响应结果
     */
    @Operation(summary = "更新系统登录信息")
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统登录信息实体", required = true) @Validated(value = UpdateGroup.class) @RequestBody SysLoginInfo sysLoginInfo) {
        this.sysLoginInfoService.updateById(sysLoginInfo);
        return ResponseResult.ok();
    }

    /**
     * 根据系统登录信息 ID 查询系统登录信息信息
     *
     * @param id 系统登录信息ID
     * @return 响应结果
     */
    @Operation(summary = "根据系统登录信息ID查询系统登录信息详情")
    @GetMapping(path = "/item/{id}")
    public ResponseResult<SysLoginInfo> getById(@Parameter(description = "系统登录信息ID", required = true) @PathVariable String id) {
        SysLoginInfo sysLoginInfo = this.sysLoginInfoService.getById(id);
        return ResponseResult.ok(sysLoginInfo);
    }

    /**
     * 分页查询
     *
     * @param sysLoginInfoPageDto 查询条件
     * @return 分页系统登录信息
     */
    @Operation(summary = "分页查询")
    @GetMapping(path = "/page")
    public ResponseResult<PageInfo<SysLoginInfo>> page(@Parameter(description = "查询信息", required = true) @RequestBody SysLoginInfoPageDto sysLoginInfoPageDto) {
        PageInfo<SysLoginInfo> pageInfo = this.sysLoginInfoService.page(sysLoginInfoPageDto);
        return ResponseResult.ok(pageInfo);
    }

}


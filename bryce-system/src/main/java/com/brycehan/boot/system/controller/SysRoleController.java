package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.validator.AddGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.vo.SysRoleVo;
import com.brycehan.boot.system.entity.SysRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 系统角色控制器
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Tag(name = "sysRole", description = "系统角色API")
@RequestMapping("/system/sysRole")
@RestController
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 保存系统角色
     *
     * @param sysRole 系统角色
     * @return 响应结果
     */
    @Operation(summary = "保存系统角色")
    @Secured(value = "ROLE_ADMIN")
    @PostMapping
    public ResponseResult<SysRole> save(@Parameter(description = "系统角色", required = true) @Validated(value = AddGroup.class) @RequestBody SysRole sysRole) {
        sysRole.setId(IdGenerator.nextId());
        this.sysRoleService.save(sysRole);
        SysRole role = this.sysRoleService.getById(sysRole.getId());
        return ResponseResult.ok(role);
    }

    /**
     * 删除系统角色
     *
     * @param id 系统角色ID
     * @return 响应结果
     */
    @Operation(summary = "删除系统角色")
    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping(path = "/{id}")
    public ResponseResult<Void> deleteById(@Parameter(description = "系统角色ID", required = true) @PathVariable String id) {
        this.sysRoleService.removeById(id);
        return ResponseResult.ok();
    }

    /**
     * 更新系统角色
     *
     * @param sysRole 系统角色
     * @return 响应结果
     */
    @Operation(summary = "更新系统角色")
    @Secured(value = "ROLE_ADMIN")
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统角色实体", required = true) @Validated(value = UpdateGroup.class) @RequestBody SysRole sysRole) {
        this.sysRoleService.updateById(sysRole);
        return ResponseResult.ok();
    }

    /**
     * 根据系统角色 ID 查询系统角色信息
     *
     * @param id 系统角色ID
     * @return 响应结果
     */
    @Operation(summary = "根据系统角色ID 查询系统角色详情")
    @Secured(value = "ROLE_ADMIN")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysRole> getById(@Parameter(description = "系统角色ID", required = true) @PathVariable String id) {
        SysRole sysRole = this.sysRoleService.getById(id);
        return ResponseResult.ok(sysRole);
    }

    /**
     * 分页查询
     *
     * @param sysRolePageDto 查询条件
     * @return 分页系统角色
     */
    @Operation(summary = "分页查询")
    @Secured(value = "ROLE_ADMIN")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysRoleVo>> page(@Parameter(description = "查询信息", required = true) @RequestBody SysRolePageDto sysRolePageDto) {
        PageResult<SysRoleVo> page = this.sysRoleService.page(sysRolePageDto);
        return ResponseResult.ok(page);
    }

}


package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.convert.SysRoleConvert;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.vo.SysMenuVo;
import com.brycehan.boot.system.entity.vo.SysRoleVo;
import com.brycehan.boot.system.entity.vo.SysUserVo;
import com.brycehan.boot.system.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色API
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Tag(name = "系统角色")
@RequestMapping("/system/role")
@RestController
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    private final SysRoleMenuService sysRoleMenuService;

    private final SysRoleDataScopeService sysRoleDataScopeService;

    private final SysUserRoleService sysUserRoleService;

    /**
     * 保存系统角色
     *
     * @param sysRoleDto 系统角色Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统角色")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("hasAuthority('system:role:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysRoleDto sysRoleDto) {
        this.sysRoleService.save(sysRoleDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统角色
     *
     * @param sysRoleDto 系统角色Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统角色")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("hasAuthority('system:role:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysRoleDto sysRoleDto) {
        this.sysRoleService.update(sysRoleDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统角色
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统角色")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("hasAuthority('system:role:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysRoleService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统角色详情
     *
     * @param id 系统角色ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统角色详情")
    @PreAuthorize("hasAuthority('system:role:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysRoleVo> get(@Parameter(description = "系统角色ID", required = true) @PathVariable Long id) {
        SysRole sysRole = this.sysRoleService.getById(id);

        SysRoleVo sysRoleVo = SysRoleConvert.INSTANCE.convert(sysRole);

        // 查询角色对应的菜单
        List<Long> menuIds = this.sysRoleMenuService.getMenuIdsByRoleId(id);
        sysRoleVo.setMenuIds(menuIds);

        // 查询角色对应的数据权限
        List<Long> orgIds = this.sysRoleDataScopeService.getOrgIdsByRoleId(id);
        sysRoleVo.setOrgIds(orgIds);

        return ResponseResult.ok(sysRoleVo);
    }

    /**
     * 系统角色分页查询
     *
     * @param sysRolePageDto 查询条件
     * @return 系统角色分页列表
     */
    @Operation(summary = "系统角色分页查询")
    @PreAuthorize("hasAuthority('system:role:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysRoleVo>> page(@Validated @RequestBody SysRolePageDto sysRolePageDto) {
        PageResult<SysRoleVo> page = this.sysRoleService.page(sysRolePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统角色导出数据
     *
     * @param sysRolePageDto 查询条件
     */
    @Operation(summary = "系统角色导出")
    @PreAuthorize("hasAuthority('system:role:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysRolePageDto sysRolePageDto) {
        this.sysRoleService.export(sysRolePageDto);
    }

    /**
     * 系统角色列表查询
     *
     * @return 系统角色列表
     */
    @Operation(summary = "列表查询")
    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/list")
    public ResponseResult<List<SysRoleVo>> list() {
        List<SysRoleVo> list = this.sysRoleService.list(new SysRolePageDto());
        return ResponseResult.ok(list);
    }

    /**
     * 角色菜单
     *
     * @return 系统角色菜单
     */
    @Operation(summary = "角色菜单")
    @PreAuthorize("hasAuthority('system:role:menu')")
    @GetMapping(path = "/menu")
    public ResponseResult<List<SysMenuVo>> menu() {
        LoginUser loginUser = LoginUserContext.currentUser();
        List<SysMenuVo> list = this.sysMenuService.getMenuTreeList(loginUser, null);
        return ResponseResult.ok(list);
    }

    /**
     * 分配数据权限
     *
     * @param dataScopeDto 数据范围Dto
     * @return 响应结果
     */
    @Operation(summary = "分配数据权限")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("hasAuthority('system:role:update')")
    @PutMapping(path = "/dataScope")
    public ResponseResult<Void> dataScope(@Validated @RequestBody SysRoleDataScopeDto dataScopeDto) {
        this.sysRoleService.dataScope(dataScopeDto);
        return ResponseResult.ok();
    }

    /**
     * 分配给用户的角色分页查询
     *
     * @param sysAssignRolePageDto 分配角色分页查询Dto
     * @return 角色分页查询
     */
    @Operation(summary = "分配给用户的角色分页查询")
    @PreAuthorize("hasAuthority('system:role:page')")
    @PostMapping(path = "/assignRole/page")
    public ResponseResult<PageResult<SysRoleVo>> assignRolePage(@Validated @RequestBody SysAssignRolePageDto sysAssignRolePageDto) {
        PageResult<SysRoleVo> page = this.sysRoleService.assignRolePage(sysAssignRolePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 分配给用户多个角色
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     * @return 响应结果
     */
    @Operation(summary = "分配给用户多个角色")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("hasAuthority('system:role:update')")
    @PostMapping(path = "/assignRole/{userId}")
    public ResponseResult<Void> assignRoleSave(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        this.sysUserRoleService.assignRoleSave(userId, roleIds);
        return ResponseResult.ok();
    }

    /**
     * 删除分配给用户的角色
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     * @return 响应结果
     */
    @Operation(summary = "删除分配给用户的角色")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("hasAuthority('system:role:delete')")
    @DeleteMapping(path = "/assignRole/{userId}")
    public ResponseResult<Void> assignRoleDelete(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        this.sysUserRoleService.deleteByUserIdAndRoleIds(userId, roleIds);
        return ResponseResult.ok();
    }

    /**
     * 角色用户分页查询
     *
     * @param pageDto 查询条件
     * @return 系统用户分页列表
     */
    @Operation(summary = "角色用户分页查询")
    @PreAuthorize("hasAuthority('system:role:page')")
    @PostMapping(path = "/user/page")
    public ResponseResult<PageResult<SysUserVo>> userPage(@Validated @RequestBody SysRoleUserPageDto pageDto) {
        PageResult<SysUserVo> page = this.sysUserService.roleUserPage(pageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 角色分配给多个用户
     *
     * @param roleId  角色ID
     * @param userIds 用户IDs
     * @return 响应结果
     */
    @Operation(summary = "分配角色给多个用户")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("hasAuthority('system:role:save')")
    @PostMapping(path = "/user/{roleId}")
    public ResponseResult<Void> saveUsers(@PathVariable Long roleId, @RequestBody List<Long> userIds) {
        this.sysUserRoleService.saveUsers(roleId, userIds);
        return ResponseResult.ok();
    }

    /**
     * 删除系统角色用户
     *
     * @param roleId  角色ID
     * @param userIds 用户IDs
     * @return 响应结果
     */
    @Operation(summary = "删除系统角色用户")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("hasAuthority('system:role:delete')")
    @DeleteMapping(path = "/user/{roleId}")
    public ResponseResult<Void> deleteUsers(@PathVariable Long roleId, @RequestBody List<Long> userIds) {
        this.sysUserRoleService.deleteByRoleIdAndUserIds(roleId, userIds);
        return ResponseResult.ok();
    }

    /**
     * 校验角色编码是否唯一
     *
     * @param sysRoleCodeDto 角色编码Dto
     * @return 响应结果，是否唯一
     */
    @Operation(summary = "校验角色编码是否唯一（true：唯一，false：不唯一）")
    @GetMapping(path = "/checkCodeUnique")
    public ResponseResult<Boolean> checkCodeUnique(@Validated SysRoleCodeDto sysRoleCodeDto) {
        boolean checked = this.sysRoleService.checkCodeUnique(sysRoleCodeDto);
        return ResponseResult.ok(checked);
    }

}
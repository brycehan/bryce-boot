package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.NotEmptyElements;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.convert.SysRoleConvert;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.po.SysUser;
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
@Validated
@RequestMapping("/system/role")
@RestController
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    private final SysRoleMenuService sysRoleMenuService;

    private final SysRoleDeptService sysRoleDeptService;

    private final SysUserRoleService sysUserRoleService;

    /**
     * 保存系统角色
     *
     * @param sysRoleDto 系统角色Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统角色")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:role:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysRoleDto sysRoleDto) {
        sysRoleService.save(sysRoleDto);
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
    @PreAuthorize("@auth.hasAuthority('system:role:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysRoleDto sysRoleDto) {
        sysRoleService.update(sysRoleDto);
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
    @PreAuthorize("@auth.hasAuthority('system:role:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysRoleService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统角色详情
     *
     * @param id 系统角色ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统角色详情")
    @PreAuthorize("@auth.hasAuthority('system:role:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysRoleVo> get(@Parameter(description = "系统角色ID", required = true) @PathVariable Long id) {
        sysRoleService.checkRoleDataScope(id);
        SysRole sysRole = sysRoleService.getById(id);

        SysRoleVo sysRoleVo = SysRoleConvert.INSTANCE.convert(sysRole);

        // 查询角色对应的菜单
        List<Long> menuIds = sysRoleMenuService.getMenuIdsByRoleId(id);
        sysRoleVo.setMenuIds(menuIds);

        // 查询角色对应的数据权限
        List<Long> deptIds = sysRoleDeptService.getDeptIdsByRoleId(id);
        sysRoleVo.setDeptIds(deptIds);

        return ResponseResult.ok(sysRoleVo);
    }

    /**
     * 系统角色分页查询
     *
     * @param sysRolePageDto 查询条件
     * @return 系统角色分页列表
     */
    @Operation(summary = "系统角色分页查询")
    @PreAuthorize("@auth.hasAuthority('system:role:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysRoleVo>> page(@Validated @RequestBody SysRolePageDto sysRolePageDto) {
        PageResult<SysRoleVo> page = sysRoleService.page(sysRolePageDto);
        return ResponseResult.ok(page);
    }

    @Operation(summary = "更改角色状态")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:role:update')")
    @PatchMapping(path = "/{id}/{status}")
    public ResponseResult<Void> updateStatus(@PathVariable Long id, @PathVariable StatusType status) {
        sysRoleService.updateStatus(id, status);
        return ResponseResult.ok();
    }

    /**
     * 系统角色导出数据
     *
     * @param sysRolePageDto 查询条件
     */
    @Operation(summary = "系统角色导出")
    @PreAuthorize("@auth.hasAuthority('system:role:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysRolePageDto sysRolePageDto) {
        sysRoleService.export(sysRolePageDto);
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
        List<SysRoleVo> list = sysRoleService.list(new SysRolePageDto());
        return ResponseResult.ok(list);
    }

    /**
     * 角色菜单
     *
     * @return 系统角色菜单
     */
    @Operation(summary = "角色菜单")
    @PreAuthorize("@auth.hasAuthority('system:role:menu')")
    @GetMapping(path = "/menu")
    public ResponseResult<List<SysMenuVo>> menu() {
        LoginUser loginUser = LoginUserContext.currentUser();
        List<SysMenuVo> list = sysMenuService.getMenuTreeList(loginUser);
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
    @PreAuthorize("@auth.hasAuthority('system:role:update')")
    @PutMapping(path = "/assignDataScope")
    public ResponseResult<Void> assignDataScope(@Validated @RequestBody SysRoleDeptDto dataScopeDto) {
        sysRoleService.assignDataScope(dataScopeDto);
        return ResponseResult.ok();
    }

    /**
     * 角色分配用户的用户分页查询
     *
     * @param sysAssignUserPageDto 查询条件
     * @return 系统用户分页列表
     */
    @Operation(summary = "角色分配用户的用户分页查询")
    @PreAuthorize("@auth.hasAuthority('system:role:update')")
    @PostMapping(path = "/assignUser/page")
    public ResponseResult<PageResult<SysUserVo>> assignUserPage(@Validated @RequestBody SysAssignUserPageDto sysAssignUserPageDto) {
        PageResult<SysUserVo> page = sysUserService.assignUserPage(sysAssignUserPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 角色分配用户，分配给角色多个用户
     *
     * @param roleId  角色ID
     * @param userIds 用户IDs
     * @return 响应结果
     */
    @Operation(summary = "角色分配用户，分配给角色多个用户")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:role:update')")
    @PostMapping(path = "/assignUser/{roleId}")
    public ResponseResult<Void> assignUserSave(@PathVariable Long roleId, @RequestBody @Parameter(description = "用户ID集合") @NotEmptyElements List<Long> userIds) {
        userIds.forEach(userId -> {
            sysUserService.checkUserAllowed(SysUser.of(userId));
            sysUserService.checkUserDataScope(SysUser.of(userId));
        });
        sysRoleService.checkRoleDataScope(roleId);
        sysUserRoleService.assignUserSave(roleId, userIds);
        return ResponseResult.ok();
    }

    /**
     * 删除分配给角色的用户
     *
     * @param roleId  角色ID
     * @param userIds 用户IDs
     * @return 响应结果
     */
    @Operation(summary = "删除分配给角色的用户")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:role:update')")
    @DeleteMapping(path = "/assignUser/{roleId}")
    public ResponseResult<Void> assignUserDelete(@PathVariable Long roleId, @RequestBody @Parameter(description = "用户ID集合") @NotEmptyElements List<Long> userIds) {
        userIds.forEach(userId -> {
            sysUserService.checkUserAllowed(SysUser.of(userId));
            sysUserService.checkUserDataScope(SysUser.of(userId));
        });
        sysRoleService.checkRoleDataScope(roleId);
        sysUserRoleService.deleteByRoleIdAndUserIds(roleId, userIds);
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
        boolean checked = sysRoleService.checkRoleCodeUnique(sysRoleCodeDto);
        return ResponseResult.ok(checked);
    }

}
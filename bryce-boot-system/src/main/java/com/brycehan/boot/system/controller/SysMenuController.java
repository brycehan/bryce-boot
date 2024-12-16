package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.common.MenuType;
import com.brycehan.boot.system.entity.convert.SysMenuConvert;
import com.brycehan.boot.system.entity.dto.SysMenuAuthorityDto;
import com.brycehan.boot.system.entity.dto.SysMenuDto;
import com.brycehan.boot.system.entity.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.po.SysMenu;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.vo.SysMenuVo;
import com.brycehan.boot.system.service.SysAuthorityService;
import com.brycehan.boot.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * 系统菜单API
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Tag(name = "系统菜单")
@RequestMapping("/system/menu")
@RestController
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final SysAuthorityService sysAuthorityService;

    /**
     * 保存系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统菜单")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:menu:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.save(sysMenuDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统菜单")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:menu:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.update(sysMenuDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统菜单
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统菜单")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:menu:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        // 判断是否有子菜单或按钮
        Long count = this.sysMenuService.getSubMenuCount(idsDto.getIds());
        if (count > 0) {
            return ResponseResult.warn("存在子菜单,不允许删除");
        }

        this.sysMenuService.delete(idsDto);

        return ResponseResult.ok();
    }

    /**
     * 查询系统菜单详情
     *
     * @param id 系统菜单ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统菜单详情")
    @PreAuthorize("@auth.hasAuthority('system:menu:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysMenuVo> get(@Parameter(description = "系统菜单ID", required = true) @PathVariable Long id) {
        SysMenu sysMenu = this.sysMenuService.getById(id);
        SysMenuVo sysMenuVo = SysMenuConvert.INSTANCE.convert(sysMenu);

        // 获取上级菜单名称
        if (sysMenu.getParentId() != 0) {
            SysMenu parent = this.sysMenuService.getById(sysMenu.getParentId());
            sysMenuVo.setParentName(parent.getName());
        }

        return ResponseResult.ok(sysMenuVo);
    }

    /**
     * 系统菜单分页查询
     *
     * @param sysMenuPageDto 查询条件
     * @return 系统菜单分页列表
     */
    @Operation(summary = "系统菜单分页查询")
    @PreAuthorize("@auth.hasAuthority('system:menu:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysMenuVo>> page(@Validated @RequestBody SysMenuPageDto sysMenuPageDto) {
        PageResult<SysMenuVo> page = this.sysMenuService.page(sysMenuPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统菜单导出数据
     *
     * @param sysMenuPageDto 查询条件
     */
    @Operation(summary = "系统菜单导出")
    @PreAuthorize("@auth.hasAuthority('system:menu:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysMenuPageDto sysMenuPageDto) {
        this.sysMenuService.export(sysMenuPageDto);
    }

    /**
     * 系统菜单列表查询
     *
     * @param sysMenuDto 查询条件
     * @return 系统菜单列表
     */
    @Operation(summary = "列表查询")
    @PreAuthorize("@auth.hasAuthority('system:menu:page')")
    @PostMapping(path = "/list")
    public ResponseResult<List<SysMenuVo>> list(@Validated @RequestBody SysMenuDto sysMenuDto) {
        List<SysMenuVo> list = this.sysMenuService.list(sysMenuDto);
        return ResponseResult.ok(list);
    }

    /**
     * 获取用户权限标识
     *
     * @return 响应结果
     */
    @Operation(summary = "获取用户权限标识", description = "用户权限标识集合")
    @GetMapping(path = "/authority")
    public ResponseResult<Set<String>> authority() {
        Set<String> authoritySet = this.sysAuthorityService.findAuthority(SysUser.of(LoginUserContext.currentUser()), false);
        return ResponseResult.ok(authoritySet);
    }

    /**
     * 获取路由信息
     *
     * @return 路由列表
     */
    @Operation(summary = "获取菜单列表")
    @GetMapping(path = "/nav")
    public ResponseResult<List<SysMenuVo>> nav() {
        List<SysMenuVo> list = this.sysMenuService.getMenuTreeList(LoginUserContext.currentUser(), MenuType.MENU.getValue());
        return ResponseResult.ok(list);
    }

    /**
     * 校验权限标识是否唯一
     *
     * @param sysMenuAuthorityDto 权限标识Dto
     * @return 响应结果，是否唯一
     */
    @Operation(summary = "校验权限标识是否唯一（true：唯一，false：不唯一）")
    @GetMapping(path = "/checkAuthorityUnique")
    public ResponseResult<Boolean> checkAuthorityUnique(@Validated SysMenuAuthorityDto sysMenuAuthorityDto) {
        boolean checked = this.sysMenuService.checkAuthorityUnique(sysMenuAuthorityDto);
        return ResponseResult.ok(checked);
    }

}


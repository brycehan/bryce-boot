package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysPermissionService;
import com.brycehan.boot.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * 系统权限服务实现类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    public SysPermissionServiceImpl(SysRoleService sysRoleService, SysMenuService sysMenuService) {
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
    }

    @Override
    public Set<String> getRolePermission(SysUser sysUser) {
        return this.sysRoleService.selectRolePermissionByUserId(sysUser.getId());
    }

    @Override
    public Set<String> getMenuPermission(SysUser sysUser) {
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            return Collections.singleton("*:*:*");
        }

        return this.sysMenuService.selectMenuPermissionByUserId(sysUser.getId());
    }
}

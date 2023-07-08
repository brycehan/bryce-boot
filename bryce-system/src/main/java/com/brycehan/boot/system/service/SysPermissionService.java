package com.brycehan.boot.system.service;

import com.brycehan.boot.system.entity.SysUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * 系统权限服务类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Validated
public interface SysPermissionService {

    /**
     * 获取用户的角色权限
     *
     * @param sysUser 系统用户
     * @return 角色权限集合
     */
    Set<String> getRolePermission(@NotNull SysUser sysUser);

    /**
     * 获取用户的菜单权限
     *
     * @param sysUser 系统用户
     * @return 菜单权限集合
     */
    Set<String> getMenuPermission(@NotNull SysUser sysUser);
}

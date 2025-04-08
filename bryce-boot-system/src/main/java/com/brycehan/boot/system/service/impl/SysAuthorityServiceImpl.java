package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.system.entity.convert.SysRoleConvert;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.SysAuthorityService;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysAuthorityServiceImpl implements SysAuthorityService {

    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;


    @Override
    public Set<String> findAuthority(SysUser sysUser, boolean findRole) {

        // 超级管理员，拥有最高权限
        if (sysUser.isSuperAdmin()) {
            sysUser.setRoleSet(Set.of(DataConstants.SUPER_ADMIN_CODE));
            return Set.of(DataConstants.ROLE_SUPER_ADMIN_CODE);
        }

        Set<String> authoritySet = new HashSet<>();

        if (findRole) {
            Set<SysRole> roles = sysRoleService.getRoleByUserId(sysUser.getId());
            // 获取角色的菜单权限
            for (SysRole role : roles) {
                Set<String> authorityByRoleId = sysMenuService.findAuthorityByRoleId(role.getId());
                role.setAuthoritySet(authorityByRoleId);
                authoritySet.addAll(authorityByRoleId);
            }

            sysUser.setRoles(SysRoleConvert.INSTANCE.convert(roles));
            sysUser.setRoleSet(roles.stream().map(SysRole::getCode).collect(Collectors.toSet()));

            Set<String> authorityRoleSet = roles.stream().map(role -> DataConstants.ROLE_PREFIX + role.getCode()).collect(Collectors.toSet());
            authoritySet.addAll(authorityRoleSet);
        } else {
            authoritySet = sysMenuService.findAuthorityByUserId(sysUser.getId());
        }

        return authoritySet;
    }
}

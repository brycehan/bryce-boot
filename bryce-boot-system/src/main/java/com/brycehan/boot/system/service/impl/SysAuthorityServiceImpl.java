package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.system.entity.convert.SysRoleConvert;
import com.brycehan.boot.system.entity.po.SysMenu;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.SysAuthorityService;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
        Set<String> authoritySet = new HashSet<>();

        // 超级管理员，拥有最高权限
        if (sysUser.isSuperAdmin()) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysMenu::getAuthority);
            queryWrapper.eq(SysMenu::getStatus, StatusType.ENABLE);

            List<String> authortityList = this.sysMenuService.listObjs(queryWrapper, Object::toString);
            return new HashSet<>(authortityList);
        }

        if (findRole) {
            Set<SysRole> roles = this.sysRoleService.getRoleByUserId(sysUser.getId());
            // 获取角色的菜单权限
            if (CollUtil.isNotEmpty(roles)) {
                for (SysRole role : roles) {
                    Set<String> authorityByRoleId = sysMenuService.findAuthorityByRoleId(role.getId());
                    role.setAuthoritySet(authorityByRoleId);
                    authoritySet.addAll(authorityByRoleId);
                }
            }

            sysUser.setRoles(SysRoleConvert.INSTANCE.convert(roles));

            Set<String> roleSet = roles.stream().map(role -> DataConstants.ROLE_PREFIX + role.getCode()).collect(Collectors.toSet());
            authoritySet.addAll(roleSet);
        } else {
            authoritySet = this.sysMenuService.findAuthorityByUserId(sysUser.getId());
        }

        return authoritySet;
    }

}

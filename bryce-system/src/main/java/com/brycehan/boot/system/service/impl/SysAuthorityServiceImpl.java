package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysAuthorityService;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统权限服务实现类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
public class SysAuthorityServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysAuthorityService {

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    public SysAuthorityServiceImpl(SysRoleService sysRoleService, SysMenuService sysMenuService) {
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
    }

    @Override
    public Set<String> getRoleAuthority(LoginUser loginUser) {
        return this.sysRoleService.selectRolePermissionByUserId(loginUser.getId());
    }

    @Override
    public Set<String> getMenuAuthority(LoginUser loginUser) {
        // 管理员拥有所有权限
        if (loginUser.getSuperAdmin()) {
            return Collections.singleton("*:*:*");
        }

        return this.sysMenuService.findAuthorityByUserId(loginUser.getId());
    }

    @Override
    public Set<String> getUserAuthority(LoginUser loginUser) {
        // 超级管理员，拥有最高权限
        Set<String> authoritySet;
        if(loginUser.getSuperAdmin()) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysMenu::getAuthority);
            // todo deleted 是否需要
            wrapper.eq(SysMenu::getDeleted, false);

            List<String> authortityList = this.sysMenuService.listObjs(wrapper, Object::toString);
            authoritySet = new HashSet<>(authortityList);
        }else {
            authoritySet = this.sysMenuService.findAuthorityByUserId(loginUser.getId());
        }
        return authoritySet;
    }
}

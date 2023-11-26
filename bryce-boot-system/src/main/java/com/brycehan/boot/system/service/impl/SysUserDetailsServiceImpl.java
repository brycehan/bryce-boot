package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.mapper.SysRoleDataScopeMapper;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysOrgService;
import com.brycehan.boot.system.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统用户详情服务实现
 *
 * @author Bryce Han
 * @since 2023/10/7
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements SysUserDetailsService {

    private final SysMenuService sysMenuService;

    private final SysOrgService sysOrgService;

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleDataScopeMapper sysRoleDataScopeMapper;

    @Override
    public UserDetails getUserDetails(LoginUser loginUser) {
        // 账号不可用
        loginUser.setEnabled(loginUser.getStatus());
        if (!loginUser.isAccountNonLocked()) {
            log.info("登录用户：{}已被锁定.", loginUser.getUsername());
            throw new ServerException(UserResponseStatus.USER_ACCOUNT_LOCKED);
        }

        // 数据权限范围
        Set<Long> dataScopeSet = this.getDataScope(loginUser);
        loginUser.setDataScopeSet(dataScopeSet);

        // 登录用户权限集合
        Set<String> authoritySet = this.sysMenuService.findAuthority(loginUser);

        // 用户角色编码列表
        Set<String> roleCodeSet = this.sysRoleMapper.getRoleCodeByUserId(loginUser.getId());
        roleCodeSet.forEach(roleCode -> authoritySet.add("ROLE_" + roleCode));

        loginUser.setAuthoritySet(authoritySet);

        return loginUser;
    }

    private Set<Long> getDataScope(LoginUser loginUser) {
        Integer dataScope = this.sysRoleMapper.getDataScopeByUserId(loginUser.getId());
        if (dataScope == null) {
            return new HashSet<>();
        }

        if (dataScope.equals(DataScopeType.ALL.value())) {
            // 全部数据范围权限，则返回null
            return null;
        } else if (dataScope.equals(DataScopeType.ORG_AND_CHILDREN.value())) {
            // 本机构及子机构数据
            List<Long> dataScopeList = this.sysOrgService.getSubOrgIds(loginUser.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(this.sysRoleDataScopeMapper.getDataScopeOrgIds(loginUser.getId()));

            return Set.copyOf(dataScopeList);
        } else if (dataScope.equals(DataScopeType.ORG_ONLY.value())) {
            // 本机构数据
            List<Long> dataScopeList = new ArrayList<>();
            dataScopeList.add(loginUser.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(this.sysRoleDataScopeMapper.getDataScopeOrgIds(loginUser.getId()));

            return Set.copyOf(dataScopeList);
        } else if (dataScope.equals(DataScopeType.CUSTOM.value())) {
            // 自定义数据权限范围
            List<Long> dataScopeOrgIds = this.sysRoleDataScopeMapper.getDataScopeOrgIds(loginUser.getId());
            return Set.copyOf(dataScopeOrgIds);
        }

        // 本人数据
        return new HashSet<>();
    }
}

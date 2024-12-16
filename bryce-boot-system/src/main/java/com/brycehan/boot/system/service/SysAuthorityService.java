package com.brycehan.boot.system.service;

import com.brycehan.boot.system.entity.po.SysUser;

import java.util.Set;

/**
 * 系统菜单权限服务
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
public interface SysAuthorityService {

    /**
     * 查询用户权限集合
     *
     * @param sysUser 系统用户
     * @return 权限集合
     */
    Set<String> findAuthority(SysUser sysUser, boolean findRole);

}

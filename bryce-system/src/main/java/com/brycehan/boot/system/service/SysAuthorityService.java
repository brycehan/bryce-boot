package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.entity.SysUser;

import java.util.Set;

/**
 * 系统权限服务类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
public interface SysAuthorityService {

    /**
     * 获取用户的角色权限
     *
     * @param loginUser 登录用户
     * @return 角色权限集合
     */
    Set<String> getRoleAuthority(LoginUser loginUser);

    /**
     * 获取用户的菜单权限
     *
     * @param loginUser 登录用户
     * @return 菜单权限集合
     */
    Set<String> getMenuAuthority(LoginUser loginUser);

    /**
     * 获取用户权限
     *
     * @param loginUser 登录用户
     * @return 用户权限
     */
    Set<String> getUserAuthority(LoginUser loginUser);

}

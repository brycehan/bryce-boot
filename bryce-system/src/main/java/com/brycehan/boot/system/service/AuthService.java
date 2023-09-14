package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.framework.security.context.LoginUser;

import java.util.Set;

/**
 * 认证服务
 *
 * @author Bryce Han
 * @since 2022/9/16
 */

public interface AuthService {

    /**
     * 登录
     *
     * @param loginDto 登录dto
     * @return 令牌 jwt token
     */
    String login(LoginDto loginDto);

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

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户
     */
    void updateLoginInfo(LoginUser loginUser);

    /**
     * 退出登录
     *
     * @param accessToken 访问令牌
     */
    void logout(String accessToken);

}

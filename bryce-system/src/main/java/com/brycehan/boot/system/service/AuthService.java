package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.dto.AccountLoginDto;
import com.brycehan.boot.common.base.dto.PhoneLoginDto;
import com.brycehan.boot.framework.security.context.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * 认证服务
 *
 * @since 2022/9/16
 * @author Bryce Han
 */

public interface AuthService {

    /**
     * 账号登录
     *
     * @param accountLoginDto 账号登录dto
     * @return 令牌 jwt token
     */
    String login(AccountLoginDto accountLoginDto);

    /**
     * 手机号登录
     *
     * @param phoneLoginDto 手机号登录dto
     * @return 令牌 jwt token
     */
    String login(PhoneLoginDto phoneLoginDto);

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
     * 更新用户登录信息
     *
     * @param loginUser 登录用户
     */
    void updateLoginInfo(UserDetails loginUser);

    /**
     * 退出登录
     *
     * @param accessToken 访问令牌
     */
    void logout(String accessToken);

}

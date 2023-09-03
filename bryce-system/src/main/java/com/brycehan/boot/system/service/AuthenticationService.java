package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.framework.security.context.LoginUser;
import jakarta.validation.constraints.NotNull;

/**
 * 认证服务
 *
 * @author Bryce Han
 * @since 2022/9/16
 */

public interface AuthenticationService {

    /**
     * 登录
     *
     * @param loginDto 登录dto
     * @return 令牌 jwt token
     */
    String login(LoginDto loginDto);

    /**
     * 更新用户登录信息
     *
     * @param id 用户ID
     */
    void updateLoginInfo(LoginUser loginUser);

    /**
     * 退出登录
     *
     * @param accessToken 访问令牌
     */
    void logout(String accessToken);

}

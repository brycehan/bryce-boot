package com.brycehan.boot.framework.service;

import com.brycehan.boot.common.base.dto.LoginDto;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * 认证服务
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Validated
public interface AuthenticationService {

    /**
     * 登录
     *
     * @param loginDto 登录dto
     * @return 令牌 jwt token
     */
    String login(@NotNull LoginDto loginDto);

    /**
     * 更新用户登录信息
     *
     * @param id 用户ID
     */
    void updateLoginInfo(@NotNull String id);

}

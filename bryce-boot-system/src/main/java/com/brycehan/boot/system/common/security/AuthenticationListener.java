package com.brycehan.boot.system.common.security;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.system.common.LoginOperateType;
import com.brycehan.boot.system.service.AuthLoginService;
import com.brycehan.boot.system.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 认证事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Component
@RequiredArgsConstructor
public class AuthenticationListener {

    private final SysLoginLogService sysLoginLogService;

    private final AuthLoginService authLoginService;

    /**
     * 登录成功事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        LoginUser loginUser = (LoginUser) event.getAuthentication().getPrincipal();
        // 记录登录日志
        this.sysLoginLogService.save(loginUser.getUsername(), DataConstants.SUCCESS, LoginOperateType.LOGIN_SUCCESS.getValue());
        // 更新用户登录信息
        this.authLoginService.updateLoginInfo(loginUser);
    }

    /**
     * 登录失败事件处理
     *
     * @param authenticationFailureEvent 认证失败事件
     */
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent authenticationFailureEvent) {
        // 用户名
        String username = (String) authenticationFailureEvent.getAuthentication().getPrincipal();
        // 记录登录日志
        this.sysLoginLogService.save(username, DataConstants.FAIL, LoginOperateType.LOGIN_SUCCESS.getValue());
    }

}

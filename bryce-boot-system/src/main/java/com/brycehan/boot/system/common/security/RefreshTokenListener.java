package com.brycehan.boot.system.common.security;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.system.entity.po.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 刷新令牌事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenListener {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 刷新令牌事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onRefreshToken(RefreshTokenEvent event) {
        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser == null) {
            return;
        }

        log.info("用户[{}]刷新令牌事件处理...", loginUser.getUsername());
        // 用户信息
        SysUser sysUser = event.getSysUser();

        // 更新用户信息
        loginUser.setNickname(sysUser.getNickname());
        loginUser.setPhone(sysUser.getPhone());
        loginUser.setEmail(sysUser.getEmail());
        loginUser.setGender(sysUser.getGender());
        loginUser.setAvatar(sysUser.getAvatar());

        jwtTokenProvider.doRefreshToken(loginUser);
    }

}

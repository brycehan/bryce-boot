package com.brycehan.boot.system.common;

import com.brycehan.boot.system.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 注册事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterListener {

    /**
     * 登录成功事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onSuccess(RegisterSuccessEvent event) {
        // 用户信息
        SysUser sysUserVo = event.getSysUser();

        log.info("用户[{}]注册成功后处理...", sysUserVo.getUsername());
    }

}

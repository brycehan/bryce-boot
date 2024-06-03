package com.brycehan.boot.system.common.security;

import com.brycehan.boot.system.entity.po.SysUser;
import org.springframework.context.ApplicationEvent;

/**
 * 刷新token事件
 *
 * @author Bryce Han
 * @since 2024/5/29
 */
public class RefreshTokenEvent extends ApplicationEvent {

    public RefreshTokenEvent(SysUser sysUser) {
        super(sysUser);
    }

    public SysUser getSysUser() {
        return (SysUser) super.getSource();
    }

}

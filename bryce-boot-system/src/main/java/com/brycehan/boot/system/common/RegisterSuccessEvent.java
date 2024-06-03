package com.brycehan.boot.system.common;

import com.brycehan.boot.system.entity.SysUser;
import org.springframework.context.ApplicationEvent;

/**
 * @author Bryce Han
 * @since 2024/5/29
 */
public class RegisterSuccessEvent extends ApplicationEvent {

    public RegisterSuccessEvent(SysUser sysUser) {
        super(sysUser);
    }

    public SysUser getSysUser() {
        return (SysUser) super.getSource();
    }
}

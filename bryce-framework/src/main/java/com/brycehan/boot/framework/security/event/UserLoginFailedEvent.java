package com.brycehan.boot.framework.security.event;

import com.brycehan.boot.common.base.dto.LoginDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户登录失败事件
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Getter
@Setter
public class UserLoginFailedEvent extends ApplicationEvent {

    private LoginDto loginUser;

    private Exception exception;

    public UserLoginFailedEvent(Object source, LoginDto loginUser, Exception exception) {
        super(source);
        this.loginUser = loginUser;
        this.exception = exception;
    }
}

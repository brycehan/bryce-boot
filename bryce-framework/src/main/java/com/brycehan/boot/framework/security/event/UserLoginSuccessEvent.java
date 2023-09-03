package com.brycehan.boot.framework.security.event;

import com.brycehan.boot.framework.security.context.LoginUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户登录成功事件
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Getter
@Setter
public class UserLoginSuccessEvent extends ApplicationEvent {

    private LoginUser loginUser;

    public UserLoginSuccessEvent(Object source, LoginUser loginUser) {
        super(source);
        this.loginUser = loginUser;
    }

}

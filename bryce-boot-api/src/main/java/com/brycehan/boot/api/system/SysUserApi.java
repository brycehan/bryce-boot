package com.brycehan.boot.api.system;

import com.brycehan.boot.common.base.LoginUser;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统用户Api
 *
 * @since 2024/4/7
 * @author Bryce Han
 */
public interface SysUserApi {

    /**
     * 获取登录对象
     *
     * @param id 用户ID
     * @return 登录对象
     */
    LoginUser loadUserById(@RequestParam Long id);

}

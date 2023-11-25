package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.security.context.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 系统用户详情服务
 *
 * @author Bryce Han
 * @since 2023/10/7
 */
public interface SysUserDetailsService {

    /**
     * 根据系统用户获取用户详情
     *
     * @param loginUser 登录用户
     * @return UserDetails实例
     */
    UserDetails getUserDetails(LoginUser loginUser);

}

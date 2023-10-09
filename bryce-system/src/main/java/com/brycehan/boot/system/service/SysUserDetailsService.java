package com.brycehan.boot.system.service;

import com.brycehan.boot.system.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 系统用户详情服务
 *
 * @since 2023/10/7
 * @author Bryce Han
 */
public interface SysUserDetailsService {

    /**
     * 根据系统用户获取用户详情
     *
     * @param sysUser 系统用户
     * @return UserDetails实例
     */
    UserDetails getUserDetails(SysUser sysUser);

}

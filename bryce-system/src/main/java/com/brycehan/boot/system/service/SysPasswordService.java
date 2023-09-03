package com.brycehan.boot.system.service;

import com.brycehan.boot.system.entity.SysUser;

/**
 * 系统密码服务
 *
 * @author Bryce Han
 * @since 2022/9/29
 */
public interface SysPasswordService {

    /**
     * 校验用户密码
     *
     * @param sysUser 系统用户
     */
    void validate(SysUser sysUser);

}

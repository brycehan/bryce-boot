package com.brycehan.boot.system.service;

import com.brycehan.boot.system.entity.SysUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 系统密码服务
 *
 * @author Bryce Han
 * @since 2022/9/29
 */
@Validated
public interface SysPasswordService {

    /**
     * 校验用户密码
     *
     * @param sysUser 系统用户
     */
    void validate(@NotNull SysUser sysUser);

}

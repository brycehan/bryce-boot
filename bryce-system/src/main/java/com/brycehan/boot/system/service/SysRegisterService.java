package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.dto.RegisterDto;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * 注册服务类
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Validated
public interface SysRegisterService {

    /**
     * 注册
     *
     * @param registerDto 注册数据传输对象
     */
    void register(@NotNull RegisterDto registerDto);

}

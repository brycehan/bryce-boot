package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.constant.ParamConstants;
import com.brycehan.boot.common.entity.dto.RegisterDto;
import com.brycehan.boot.common.enums.CaptchaType;
import com.brycehan.boot.system.common.security.RegisterSuccessEvent;
import com.brycehan.boot.system.entity.dto.SysUsernameDto;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.AuthCaptchaService;
import com.brycehan.boot.system.service.AuthRegisterService;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 系统注册服务实现类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthRegisterServiceImpl implements AuthRegisterService {

    private final SysUserService sysUserService;

    private final SysParamService sysParamService;

    private final AuthCaptchaService authCaptchaService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void register(RegisterDto registerDto) {
        // 验证码开关
        boolean validated = this.validate(registerDto.getKey(), registerDto.getCode());
        if (!validated) {
            throw new RuntimeException("验证码错误");
        }

        // 注册
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(registerDto, sysUser);

        sysUser = sysUserService.registerUser(sysUser);

        log.info("注册成功，用户名：{}", registerDto.getUsername());
        this.applicationEventPublisher.publishEvent(new RegisterSuccessEvent(sysUser));
    }

    @Override
    public boolean registerEnabled() {
        return sysParamService.getBoolean(ParamConstants.SYSTEM_REGISTER_ENABLED);
    }

    @Override
    public boolean validate(String key, String code) {
        return this.authCaptchaService.validate(key, code, CaptchaType.REGISTER);
    }

    @Override
    public boolean checkUsernameUnique(String username) {
        SysUsernameDto sysUsernameDto = new SysUsernameDto();
        sysUsernameDto.setUsername(username);
        return sysUserService.checkUsernameUnique(sysUsernameDto);
    }

}

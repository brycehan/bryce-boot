package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.base.dto.RegisterDto;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.CaptchaService;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.service.SysRegisterService;
import com.brycehan.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class SysRegisterServiceImpl implements SysRegisterService {

    private final SysUserService sysUserService;

    private final SysParamService sysParamService;

    private final PasswordEncoder passwordEncoder;

    private final CaptchaService captchaService;

    @Override
    public void register(RegisterDto registerDto) {
        // 验证码开关
        boolean validated = this.validate(registerDto.getKey(), registerDto.getCode());
        if (!validated) {
            throw new RuntimeException("验证码错误");
        }

        // 用户账号唯一校验
        SysUser sysUser = new SysUser();
        sysUser.setUsername(registerDto.getUsername().trim());
        boolean usernameUnique = this.sysUserService.checkUsernameUnique(sysUser);
        if (!usernameUnique) {
            throw new ServerException(UserResponseStatus.USER_REGISTER_EXISTS, sysUser.getUsername());
        }

        // 注册
        sysUser.setFullName(sysUser.getUsername());
        sysUser.setPassword(passwordEncoder.encode(registerDto.getPassword().trim()));
        this.sysUserService.registerUser(sysUser);
    }

    @Override
    public boolean validate(String key, String code) {
        return this.captchaService.validate(key, code);
    }

    @Override
    public boolean isCaptchaEnabled() {
        return this.sysParamService.getBoolean("system.account.captchaEnabled");
    }

}

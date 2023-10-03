package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.base.dto.RegisterDto;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.exception.user.UserCaptchaException;
import com.brycehan.boot.common.exception.user.UserCaptchaExpireException;
import com.brycehan.boot.common.util.PasswordUtils;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.service.SysRegisterService;
import com.brycehan.boot.system.service.SysUserService;
import com.brycehan.boot.system.entity.SysUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统注册服务实现类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
@Service
public class SysRegisterServiceImpl implements SysRegisterService {

    private final SysUserService sysUserService;

    private final SysParamService sysParamService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SysRegisterServiceImpl(SysUserService sysUserService, SysParamService sysParamService) {
        this.sysUserService = sysUserService;
        this.sysParamService = sysParamService;
    }

    @Override
    public void register(RegisterDto registerDto) {
        // 1、验证码开关
        boolean captchaEnabled = this.sysParamService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(registerDto.getUuid(), registerDto.getCode());
        }
        // 2、用户账号唯一校验
        SysUser sysUser = new SysUser();
        sysUser.setUsername(registerDto.getUsername().trim());
        boolean usernameUnique = this.sysUserService.checkUsernameUnique(sysUser);
        if (!usernameUnique) {
            throw BusinessException.responseStatus(UserResponseStatus.USER_REGISTER_EXISTS, sysUser.getUsername());
        }
        // 3、注册
        sysUser.setFullName(sysUser.getUsername());
        sysUser.setPassword(PasswordUtils.encode(registerDto.getPassword().trim()));
        this.sysUserService.registerUser(sysUser);
    }

    /**
     * 校验验证码
     *
     * @param uuid 唯一标识
     * @param code 验证码
     */
    private void validateCaptcha(String uuid, String code) {
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(captchaKey);
        if (Objects.isNull(captchaValue)) {
            throw new UserCaptchaExpireException();
        } else if (!captchaValue.equalsIgnoreCase(code)) {
            throw new UserCaptchaException();
        }
    }

}

package com.brycehan.boot.framework.service.impl;

import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.exception.user.UserCaptchaException;
import com.brycehan.boot.common.exception.user.UserCaptchaExpireException;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.MessageUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.event.UserLoginFailedEvent;
import com.brycehan.boot.framework.security.event.UserLoginSuccessEvent;
import com.brycehan.boot.framework.service.AuthenticationService;
import com.brycehan.boot.system.context.LoginUser;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysConfigService;
import com.brycehan.boot.system.service.SysLoginInfoService;
import com.brycehan.boot.system.service.SysUserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Bryce Han
 * @since 2022/9/16
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final StringRedisTemplate stringRedisTemplate;

    private final SysConfigService sysConfigService;

    private final SysLoginInfoService sysLoginInfoService;

    private final SysUserService sysUserService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String login(@NotNull LoginDto loginDto) {

        // 1、验证码开关
        boolean captchaEnabled = this.sysConfigService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(loginDto.getUsername(),
                    loginDto.getUuid(),
                    loginDto.getCode());
        }

        // 2、账号密码验证
        Authentication authentication;
        try {
            // 1）设置需要认证的用户信息
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            authentication = this.authenticationManager.authenticate(authenticationToken);
            // 2）更新当前用户为已经认证成功
//            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException e) {
            // 发布登录失败事件
            applicationEventPublisher.publishEvent(new UserLoginFailedEvent(this, loginDto, e));

            if(e.getCause() instanceof BusinessException){
                throw (BusinessException) e.getCause();
            }else {
                throw BusinessException.builder()
                        .module("system")
                        .code(UserResponseStatusEnum.USER_USERNAME_OR_PASSWORD_ERROR.code())
                        .message(e.getMessage())
                        .build();
            }
        }

        // 发布登录成功事件
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        applicationEventPublisher.publishEvent(new UserLoginSuccessEvent(this, loginUser));

        // 3、生成令牌token
        return this.jwtTokenProvider.generateToken(loginUser);
    }

    @Override
    public void updateLoginInfo(@NotNull String id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setLastLoginIp(IpUtils.getIpAddress(ServletUtils.getRequest()));
        sysUser.setLastLoginTime(LocalDateTime.now());
        sysUserService.updateById(sysUser);
    }

    /**
     * 校验验证码
     *
     * @param username 账号
     * @param uuid     唯一标识
     * @param code     验证码
     */
    private void validateCaptcha(String username, String uuid, String code) {
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(captchaKey);
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        if (Objects.isNull(captchaValue)) {
            sysLoginInfoService.AsyncRecordLoginInfo(userAgent, ip, username, CommonConstants.LOGIN_FAIL, MessageUtils.getMessage("user.captcha.expire"));
            throw new UserCaptchaExpireException();
        } else if (!captchaValue.equalsIgnoreCase(code)) {
            sysLoginInfoService.AsyncRecordLoginInfo(userAgent, ip, username, CommonConstants.LOGIN_FAIL, MessageUtils.getMessage("user.captcha.error"));
            throw new UserCaptchaException();
        }
    }

}

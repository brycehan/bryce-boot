package com.brycehan.boot.framework.service.impl;

import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.exception.user.UserCaptchaException;
import com.brycehan.boot.common.exception.user.UserCaptchaExpireException;
import com.brycehan.boot.common.util.HttpContextUtils;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.MessageUtils;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.event.UserLoginFailedEvent;
import com.brycehan.boot.framework.security.event.UserLoginSuccessEvent;
import com.brycehan.boot.framework.service.AuthenticationService;
import com.brycehan.boot.system.context.LoginUser;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysConfigService;
import com.brycehan.boot.system.service.SysLoginInfoService;
import com.brycehan.boot.system.service.SysUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Bryce Han
 * @since 2022/9/16
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final ThreadPoolExecutor executor;

    private final SysConfigService sysConfigService;

    private final SysLoginInfoService sysLoginInfoService;

    private final SysUserService sysUserService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthenticationServiceImpl(AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider, ThreadPoolExecutor executor, SysConfigService sysConfigService, SysLoginInfoService sysLoginInfoService, SysUserService sysUserService, ApplicationEventPublisher applicationEventPublisher) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.executor = executor;
        this.sysConfigService = sysConfigService;
        this.sysLoginInfoService = sysLoginInfoService;
        this.sysUserService = sysUserService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public String login(LoginDto loginDto) {
        // 1、验证码开关
        boolean captchaEnabled = this.sysConfigService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(loginDto.getUsername(),
                    loginDto.getUuid(),
                    loginDto.getCode());
        }
        // 2、账号密码验证
        Authentication authentication;
        // 子线程共享请求request数据
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        try {
            // 1）设置需要认证的用户信息
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 2）该方法会调用UserDetailsServiceImpl.loadUserByUsername方法
            authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // 3）更新当前用户为已经认证成功
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
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
    public void updateLoginInfo(String id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setLastLoginIp(IpUtils.getIpAddress(HttpContextUtils.getRequest()));
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
        String userAgent = HttpContextUtils.getRequest().getHeader("User-Agent");
        if (Objects.isNull(captchaValue)) {
            sysLoginInfoService.AsyncRecordLoginInfo(userAgent, username, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.captcha.expire"));
            throw new UserCaptchaExpireException();
        } else if (!captchaValue.equalsIgnoreCase(code)) {
            sysLoginInfoService.AsyncRecordLoginInfo(userAgent, username, CommonConstants.LOGIN_FAIL, MessageUtils.message("user.captcha.error"));
            throw new UserCaptchaException();
        }
    }

}

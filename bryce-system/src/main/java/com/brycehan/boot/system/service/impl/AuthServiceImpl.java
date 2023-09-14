package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.event.UserLoginFailedEvent;
import com.brycehan.boot.framework.security.event.UserLoginSuccessEvent;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.service.*;
import com.brycehan.boot.system.entity.SysUser;
import jakarta.annotation.Resource;
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
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Bryce Han
 * @since 2022/9/16
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final SysConfigService sysConfigService;

    private final SysLoginInfoService sysLoginInfoService;

    private final SysUserService sysUserService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    @Override
    public String login(@NotNull LoginDto loginDto) {

        // 1、验证码开关
//        boolean captchaEnabled = this.sysConfigService.selectCaptchaEnabled();
//        if (captchaEnabled) {
//            validateCaptcha(loginDto.getUsername(),
//                    loginDto.getUuid(),
//                    loginDto.getCode());
//        }

        // 2、账号密码验证
        Authentication authentication;
        // 子线程共享请求request数据
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
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
    public Set<String> getRoleAuthority(LoginUser loginUser) {
        return this.sysRoleService.selectRolePermissionByUserId(loginUser.getId());
    }

    @Override
    public Set<String> getMenuAuthority(LoginUser loginUser) {
        // 管理员拥有所有权限
        if (loginUser.getSuperAdmin()) {
            return Collections.singleton("*:*:*");
        }

        return this.sysMenuService.findAuthority(loginUser);
    }

    @Override
    public Set<String> getUserAuthority(LoginUser loginUser) {
        // 超级管理员，拥有最高权限
        Set<String> authoritySet;
        if(loginUser.getSuperAdmin()) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysMenu::getAuthority);
            // todo deleted 是否需要
            wrapper.eq(SysMenu::getDeleted, false);

            List<String> authortityList = this.sysMenuService.listObjs(wrapper, Object::toString);
            authoritySet = new HashSet<>(authortityList);
        }else {
            authoritySet = this.sysMenuService.findAuthority(loginUser);
        }
        return authoritySet;
    }

    @Override
    public void updateLoginInfo(LoginUser user) {
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
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

    @Override
    public void logout(String accessToken) {

        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(accessToken);
        if (Objects.nonNull(loginUser)) {
            // 1、删除登录用户缓存记录
            this.jwtTokenProvider.deleteLoginUser(loginUser.getToken());

            // 2、记录用户退出日志
//            sysLoginInfoService.AsyncRecordLoginInfo(loginUser.getUsername(), CommonConstants.LOGOUT_SUCCESS);
        }
    }

}

package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.exception.user.UserCaptchaException;
import com.brycehan.boot.common.exception.user.UserCaptchaExpireException;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.enums.LoginInfoType;
import com.brycehan.boot.system.service.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @since 2022/9/16
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final StringRedisTemplate stringRedisTemplate;

    private final SysParamService sysParamService;

    private final SysLoginLogService sysLoginLogService;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    @Override
    public String login(@NotNull LoginDto loginDto) {

        // 1、验证码开关
//        boolean captchaEnabled = this.sysParamService.selectCaptchaEnabled();
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

            if(e.getCause() instanceof BusinessException){
                throw (BusinessException) e.getCause();
            }else {
                throw BusinessException.builder()
                        .module("system")
                        .code(UserResponseStatus.USER_USERNAME_OR_PASSWORD_ERROR.code())
                        .message(e.getMessage())
                        .build();
            }
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 2、生成令牌token
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
    public void updateLoginInfo(UserDetails user) {
        SysUser sysUser = new SysUser();
        sysUser.setLastLoginIp(IpUtils.getIpAddress(ServletUtils.getRequest()));
        sysUser.setLastLoginTime(LocalDateTime.now());

        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", user.getUsername());

        this.sysUserService.update(sysUser, updateWrapper);
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

        if (Objects.isNull(captchaValue)) {
            this.sysLoginLogService.save(username, CommonConstants.LOGIN_FAIL, LoginInfoType.CAPTCHA_FAIL.getValue());
            throw new UserCaptchaExpireException();
        } else if (!captchaValue.equalsIgnoreCase(code)) {
            this.sysLoginLogService.save(username, CommonConstants.LOGIN_FAIL, LoginInfoType.CAPTCHA_FAIL.getValue());
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
            this.sysLoginLogService.save(loginUser.getUsername(), DataConstants.SUCCESS, LoginInfoType.LOGOUT_SUCCESS.getValue());
        }
    }

}

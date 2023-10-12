package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.brycehan.boot.common.base.dto.AccountLoginDto;
import com.brycehan.boot.common.base.dto.PhoneLoginDto;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.phone.PhoneCodeAuthenticationToken;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.enums.LoginInfoType;
import com.brycehan.boot.system.service.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final SysLoginLogService sysLoginLogService;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final CaptchaService captchaService;

    private final PasswordRetryService passwordRetryService;

    @Override
    public String login(@NotNull AccountLoginDto accountLoginDto) {

        // 1、校验验证码
        boolean validated = this.captchaService.validate(accountLoginDto.getKey(), accountLoginDto.getCode());
        if(!validated) {
            // 保存登录日志
            this.sysLoginLogService.save(accountLoginDto.getUsername(), DataConstants.FAIL, LoginInfoType.CAPTCHA_FAIL.getValue());
            throw new RuntimeException("验证码错误");
        }

        // 2、账号密码验证
        Authentication authentication;
        try {
            // 3、设置需要认证的用户信息
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountLoginDto.getUsername(), accountLoginDto.getPassword());
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("认证失败，{}", e.getMessage());
            // 添加密码错误重试缓存
            this.passwordRetryService.retryCount(accountLoginDto.getUsername());
            throw e;
        }

        // 清除密码错误重试缓存
        this.passwordRetryService.deleteCount(accountLoginDto.getUsername());

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 4、生成令牌token
        return this.jwtTokenProvider.generateToken(loginUser);
    }

    @Override
    public String login(PhoneLoginDto phoneLoginDto) {
        // 1、账号密码验证
        Authentication authentication;
        try {
            // 2、设置需要认证的用户信息
            PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(phoneLoginDto.getPhone(), phoneLoginDto.getCode());
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("认证失败，{}", e.getMessage());
            throw new RuntimeException("手机号或验证码错误");
        }

        // 用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

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
    public void updateLoginInfo(UserDetails user) {
        SysUser sysUser = new SysUser();
        sysUser.setLastLoginIp(IpUtils.getIp(ServletUtils.getRequest()));
        sysUser.setLastLoginTime(LocalDateTime.now());

        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", user.getUsername());

        this.sysUserService.update(sysUser, updateWrapper);
    }

    @Override
    public void logout(String accessToken) {

        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(accessToken);
        if (Objects.nonNull(loginUser)) {
            // 1、删除登录用户缓存记录
            this.jwtTokenProvider.deleteLoginUser(loginUser.getTokenKey());

            // 2、记录用户退出日志
            this.sysLoginLogService.save(loginUser.getUsername(), DataConstants.SUCCESS, LoginInfoType.LOGOUT_SUCCESS.getValue());
        }
    }

}

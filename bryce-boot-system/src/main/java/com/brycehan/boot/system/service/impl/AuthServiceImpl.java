package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.brycehan.boot.common.base.dto.AccountLoginDto;
import com.brycehan.boot.common.base.dto.PhoneLoginDto;
import com.brycehan.boot.common.base.vo.LoginVo;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.phone.PhoneCodeAuthenticationToken;
import com.brycehan.boot.system.common.LoginOperateType;
import com.brycehan.boot.system.entity.SysUser;
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

import java.time.LocalDateTime;
import java.util.Objects;

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

    private final CaptchaService captchaService;

    private final SysPasswordRetryService sysPasswordRetryService;

    @Override
    public LoginVo loginByAccount(@NotNull AccountLoginDto accountLoginDto) {
        // 校验验证码
        boolean validated = this.captchaService.validate(accountLoginDto.getKey(), accountLoginDto.getCode());
        if (!validated) {
            // 保存登录日志
            this.sysLoginLogService.save(accountLoginDto.getUsername(), DataConstants.FAIL, LoginOperateType.CAPTCHA_FAIL.getValue());
            throw new RuntimeException("验证码错误");
        }

        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountLoginDto.getUsername(), accountLoginDto.getPassword()));
        } catch (AuthenticationException e) {
            log.info("认证失败，{}", e.getMessage());
            // 添加密码错误重试缓存
            this.sysPasswordRetryService.retryCount(accountLoginDto.getUsername());
            throw new RuntimeException("用户名或密码错误");
        }

        // 清除密码错误重试缓存
        this.sysPasswordRetryService.deleteCount(accountLoginDto.getUsername());

        return getLoginVo(authentication);
    }

    @Override
    public LoginVo loginByPhone(PhoneLoginDto phoneLoginDto) {
        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(phoneLoginDto.getPhone(), phoneLoginDto.getCode());
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("认证失败，{}", e.getMessage());
            throw new RuntimeException("手机号或验证码错误");
        }

        return getLoginVo(authentication);
    }

    /**
     * 通过认证信息获取登录 Vo
     *
     * @param authentication 认证信息
     * @return 登录 Vo
     */
    private LoginVo getLoginVo(Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成 jwt 令牌
        LoginVo loginVo = this.jwtTokenProvider.generateToken(loginUser);

        // 缓存 loginUser
        this.jwtTokenProvider.cacheLoginUser(loginUser);

        return loginVo;
    }

    @Override
    public void updateLoginInfo(UserDetails user) {
        SysUser sysUser = new SysUser();
        sysUser.setLastLoginIp(IpUtils.getIp(ServletUtils.getRequest()));
        sysUser.setLastLoginTime(LocalDateTime.now());

        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUsername, user.getUsername());

        this.sysUserService.update(sysUser, updateWrapper);
    }

    @Override
    public void logout(String accessToken) {
        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(accessToken);

        if (Objects.nonNull(loginUser)) {
            // 删除登录用户缓存记录
            this.jwtTokenProvider.deleteLoginUser(loginUser.getTokenKey());

            // 记录用户退出日志
            this.sysLoginLogService.save(loginUser.getUsername(), DataConstants.SUCCESS, LoginOperateType.LOGOUT_SUCCESS.getValue());
        }
    }

}

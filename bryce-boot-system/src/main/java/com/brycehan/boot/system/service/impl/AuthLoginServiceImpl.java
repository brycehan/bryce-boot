package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.common.entity.dto.AccountLoginDto;
import com.brycehan.boot.common.entity.dto.PhoneLoginDto;
import com.brycehan.boot.common.entity.vo.LoginVo;
import com.brycehan.boot.common.enums.CaptchaType;
import com.brycehan.boot.common.enums.LoginStatus;
import com.brycehan.boot.common.enums.OperateStatus;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.phone.PhoneCodeAuthenticationToken;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @since 2022/9/16
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLoginServiceImpl implements AuthLoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final SysLoginLogService sysLoginLogService;

    private final SysUserService sysUserService;

    private final AuthCaptchaService authCaptchaService;

    private final AuthPasswordRetryService authPasswordRetryService;

    @Override
    public LoginVo loginByAccount(AccountLoginDto accountLoginDto) {
        log.debug("loginByAccount，账号认证");
        // 校验验证码
        boolean validated = authCaptchaService.validate(accountLoginDto.getKey(), accountLoginDto.getCode(), CaptchaType.LOGIN);
        if (!validated) {
            // 保存登录日志
            sysLoginLogService.save(accountLoginDto.getUsername(), OperateStatus.FAIL, LoginStatus.CAPTCHA_FAIL);
            throw new RuntimeException("验证码错误");
        }

        // 设置记住我
        LoginUserContext.rememberMeHolder.set(Boolean.TRUE.equals(accountLoginDto.getRememberMe()));

        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountLoginDto.getUsername(), accountLoginDto.getPassword()));
        } catch (AuthenticationException e) {
            log.info("loginByAccount，认证失败，{}", e.getMessage());
            // 添加密码错误重试缓存
            authPasswordRetryService.retryCount(accountLoginDto.getUsername());
            throw new RuntimeException("用户名或密码错误");
        }

        // 清除密码错误重试缓存
        authPasswordRetryService.deleteCount(accountLoginDto.getUsername());

        return loadLoginVo(authentication);
    }

    @Override
    public LoginVo loginByPhone(PhoneLoginDto phoneLoginDto) {
        // 设置记住我
        LoginUserContext.rememberMeHolder.set(Boolean.TRUE.equals(phoneLoginDto.getRememberMe()));

        Authentication authentication;
        try {
            // 设置需要认证的用户信息
            PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(phoneLoginDto.getPhone(), phoneLoginDto.getCode());
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.info("loginByPhone，认证失败，{}", e.getMessage());
            throw new RuntimeException("手机号或验证码错误");
        }

        return loadLoginVo(authentication);
    }

    @Override
    public void refreshToken() {
        jwtTokenProvider.doRefreshToken(LoginUserContext.currentUser());
    }

    /**
     * 通过认证信息获取登录Vo
     *
     * @param authentication 认证信息
     * @return 登录Vo
     */
    private LoginVo loadLoginVo(Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成 jwt
        String token = jwtTokenProvider.generateToken(loginUser);

        // 缓存 loginUser
        jwtTokenProvider.cache(loginUser);

        // 封装 LoginVo
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(loginUser, loginVo);
        loginVo.setAccessToken(JwtConstants.TOKEN_PREFIX.concat(token));
        loginVo.setExpiresIn(Duration.between(loginUser.getLoginTime(), loginUser.getExpireTime()).toSeconds());

        return loginVo;
    }

    @Override
    public void updateLoginInfo(LoginUser loginUser) {
        SysUser sysUser = new SysUser();
        sysUser.setLastLoginIp(loginUser.getLoginIp());
        sysUser.setLastLoginTime(LocalDateTime.now());
        sysUser.setUpdatedUserId(loginUser.getId());

        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUsername, loginUser.getUsername());

        sysUserService.update(sysUser, updateWrapper);
    }

    @Override
    public void logout(LoginUser loginUser) {
        if (Objects.isNull(loginUser)) {
            return;
        }

        if (StringUtils.isNotEmpty(loginUser.getUserKey())) {
            // 删除登录用户缓存记录
            jwtTokenProvider.deleteLoginUser(loginUser.getUserKey());
        }

        // 记录用户退出日志
        sysLoginLogService.save(loginUser.getUsername(), OperateStatus.SUCCESS, LoginStatus.LOGOUT_SUCCESS);
    }

}

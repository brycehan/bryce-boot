package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.entity.dto.AccountLoginDto;
import com.brycehan.boot.common.entity.dto.PhoneLoginDto;
import com.brycehan.boot.common.entity.vo.LoginVo;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import com.brycehan.boot.system.entity.vo.SysUserVo;
import com.brycehan.boot.system.service.AuthLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录认证API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "登录认证")
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthLoginController {

    private final AuthLoginService authLoginService;

    /**
     * 账号登录
     *
     * @param accountLoginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "账号密码登录")
    @PostMapping(path = "/loginByAccount")
    public ResponseResult<LoginVo> loginByAccount(@Validated @RequestBody AccountLoginDto accountLoginDto) {
        LoginVo loginVo = authLoginService.loginByAccount(accountLoginDto);
        return ResponseResult.ok(loginVo);
    }

    /**
     * 手机验证码登录
     *
     * @param phoneLoginDto 手机验证码登录dto
     * @return 响应结果
     */
    @Operation(summary = "手机验证码登录")
    @PostMapping(path = "/loginByPhone")
    public ResponseResult<LoginVo> loginByPhone(@Validated @RequestBody PhoneLoginDto phoneLoginDto) {
        LoginVo loginVo = authLoginService.loginByPhone(phoneLoginDto);
        return ResponseResult.ok(loginVo);
    }

    /**
     * 刷新令牌
     *
     * @return 响应结果
     */
    @Operation(summary = "刷新令牌")
    @GetMapping(path = "/refreshToken")
    public ResponseResult<LoginVo> refreshToken() {
        authLoginService.refreshToken();
        return ResponseResult.ok();
    }

    /**
     * 查询系统登录用户详情
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录用户详情")
    @GetMapping(path = "/currentUser")
    public ResponseResult<SysUserVo> currentUser() {
        SysUserVo sysUserVo = SysUserConvert.INSTANCE.convert(LoginUserContext.currentUser());
        return ResponseResult.ok(sysUserVo);
    }

    /**
     * 退出登录
     *
     * @return 响应结果
     */
    @Operation(summary = "退出登录")
    @GetMapping(path = "/logout")
    public ResponseResult<Void> logout() {
        this.authLoginService.logout(LoginUserContext.currentUser());
        return ResponseResult.ok();
    }

}

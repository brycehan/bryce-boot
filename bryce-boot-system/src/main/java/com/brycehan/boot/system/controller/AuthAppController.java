package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.context.LoginUserContext;
import com.brycehan.boot.common.base.dto.AccountLoginDto;
import com.brycehan.boot.common.base.dto.PhoneLoginDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.vo.LoginVo;
import com.brycehan.boot.system.convert.SysUserConvert;
import com.brycehan.boot.system.service.AuthService;
import com.brycehan.boot.system.vo.SysUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * App登录认证API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "App登录认证")
@RestController
@RequestMapping(path = "/auth/app")
@RequiredArgsConstructor
public class AuthAppController {

    private final AuthService authService;

    /**
     * app账号登录
     *
     * @param accountLoginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "app账号密码登录")
    @PostMapping(path = "/account/login")
    public ResponseResult<LoginVo> accountLogin(@Validated @RequestBody AccountLoginDto accountLoginDto) {
        LoginVo userLoginVo = authService.appLoginByAccount(accountLoginDto);
        return ResponseResult.ok(userLoginVo);
    }

    /**
     * 手机验证码登录
     *
     * @param phoneLoginDto 手机验证码登录dto
     * @return 响应结果
     */
    @Operation(summary = "手机验证码登录")
    @PostMapping(path = "/sms/login")
    public ResponseResult<LoginVo> smsLogin(@Validated @RequestBody PhoneLoginDto phoneLoginDto) {
        LoginVo loginVo = authService.loginByPhone(phoneLoginDto);
        return ResponseResult.ok(loginVo);
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

}

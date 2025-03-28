package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.response.SystemResponseStatus;
import com.brycehan.boot.common.entity.dto.RegisterDto;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.service.AuthRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统注册API
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Tag(name = "注册")
@RequestMapping(path = "/auth/register")
@RestController
@RequiredArgsConstructor
public class AuthRegisterController {

    private final AuthRegisterService authRegisterService;

    /**
     * 注册
     *
     * @param registerDto 注册dto
     * @return 响应结果
     */
    @Operation(summary = "注册")
    @OperateLog(type = OperatedType.INSERT)
    @PostMapping
    public ResponseResult<Void> register(@Parameter(description = "注册参数", required = true) @Validated @RequestBody RegisterDto registerDto) {
        // 查询注册开关
        if (authRegisterService.registerEnabled()) {
            // 注册
            authRegisterService.register(registerDto);
            return ResponseResult.ok();
        }

        return ResponseResult.error(SystemResponseStatus.USER_REGISTER_NOT_ENABLED);
    }

    /**
     * 获取注册开关
     *
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "获取注册开关")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> registerEnabled() {
        boolean captchaEnabled = authRegisterService.registerEnabled();
        return ResponseResult.ok(captchaEnabled);
    }

    /**
     * 校验用户账号是否可注册
     *
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验用户账号是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkUnique/{username}")
    public ResponseResult<Boolean> checkUsernameUnique(@PathVariable String username) {
        boolean checked = authRegisterService.checkUsernameUnique(username);
        return ResponseResult.ok(checked);
    }

}


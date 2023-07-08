package com.brycehan.boot.admin.server.controller;

import com.brycehan.boot.common.base.dto.RegisterDto;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.system.service.SysConfigService;
import com.brycehan.boot.system.service.SysRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统注册控制器
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Tag(name = "register", description = "注册API")
@RequestMapping(path = "/register")
@RestController
public class RegisterController {

    private final SysRegisterService sysRegisterService;

    private final SysConfigService sysConfigService;

    public RegisterController(SysRegisterService sysRegisterService, SysConfigService sysConfigService) {
        this.sysRegisterService = sysRegisterService;
        this.sysConfigService = sysConfigService;
    }

    /**
     * 注册
     *
     * @param registerDto 注册dto
     * @return 响应结果
     */
    @Operation(summary = "注册")
    @PostMapping
    public ResponseResult<Void> register(@Parameter(description = "注册参数", required = true) @Validated @RequestBody RegisterDto registerDto) {
        // 1、查询注册开关
        String registerEnabled = this.sysConfigService.selectConfigValueByConfigKey("sys.account.registerEnabled");
        if (Boolean.parseBoolean(registerEnabled)) {
            // 2、注册
            this.sysRegisterService.register(registerDto);
            return ResponseResult.ok();
        }

        return ResponseResult.error(UserResponseStatusEnum.USER_REGISTER_NOT_ENABLED);
    }

    /**
     * 获取注册开关
     *
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "获取注册开关")
    @GetMapping(path = "enabled")
    public ResponseResult<Boolean> registerEnabled() {
        // 1、查询注册开关
        String registerEnabled = this.sysConfigService.selectConfigValueByConfigKey("sys.account.registerEnabled");
        return ResponseResult.ok(Boolean.parseBoolean(registerEnabled));
    }

}


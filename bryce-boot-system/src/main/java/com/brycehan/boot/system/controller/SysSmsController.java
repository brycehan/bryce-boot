package com.brycehan.boot.system.controller;

import com.brycehan.boot.api.sms.SmsApi;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.system.service.SysUserService;
import com.brycehan.boot.system.vo.SysUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * 短信控制器
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "短信")
@RequestMapping("/system/sms")
@RestController
@RequiredArgsConstructor
public class SysSmsController {

    private final SmsApi smsApi;

    private final SysUserService sysUserService;

    @Value("${sms.login-template-id}")
    private String loginTemplateId;

    @Value("${sms.register-template-id}")
    private String registerTemplateId;

    /**
     * 生成登录验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成登录验证码")
    @GetMapping(path = "/login/code")
    public ResponseResult<?> sendLoginCode(String phone) {
        return sendCode(phone, loginTemplateId);
    }

    /**
     * 生成注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成注册验证码")
    @GetMapping(path = "/register/code")
    public ResponseResult<?> sendRegisterCode(String phone) {
        return sendCode(phone, registerTemplateId);
    }

    /**
     * 是否开启短信功能
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启短信功能")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> enabled() {
        boolean enabled = this.smsApi.isSmsEnabled();
        return ResponseResult.ok(enabled);
    }

    @NotNull
    private ResponseResult<?> sendCode(String phone, String templateId) {
        boolean smsEnabled = this.smsApi.isSmsEnabled();
        if (!smsEnabled) {
            return ResponseResult.error("短信功能未开启");
        }

        SysUserVo sysUserVo = this.sysUserService.getByPhone(phone);
        if(sysUserVo == null) {
            throw new RuntimeException("手机号码未注册");
        }

        // 生成6位验证码
        String code = RandomStringUtils.randomNumeric(6);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", code);

        // 发送短信
        boolean result = this.smsApi.send(phone, templateId, params);

        return ResponseResult.ok(result);
    }

}

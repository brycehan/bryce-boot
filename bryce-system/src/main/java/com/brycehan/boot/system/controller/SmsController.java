package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.system.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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
@Tag(name = "sms", description = "短信API")
@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

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

        String code = RandomStringUtils.randomNumeric(6);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", code);

        return this.smsService.send(phone, "login", loginTemplateId, params);
    }

    /**
     * 生成注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成注册验证码")
    @GetMapping(path = "/register/code")
    public ResponseResult<?> sendRegisterCode(String phone) {
        String code = RandomStringUtils.randomNumeric(6);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", code);

        return this.smsService.send(phone, "register", registerTemplateId, params);
    }

    /**
     * 是否开启短信功能
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启短信功能")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> enabled() {
        boolean enabled = this.smsService.isSmsEnabled();
        return ResponseResult.ok(enabled);
    }

}

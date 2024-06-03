package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.enums.CaptchaType;
import com.brycehan.boot.system.service.AuthCaptchaService;
import com.brycehan.boot.system.vo.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "验证码")
@RequestMapping("/auth/captcha")
@RestController
@RequiredArgsConstructor
public class AuthCaptchaController {

    private final AuthCaptchaService authCaptchaService;

    /**
     * 生成验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成验证码")
    @GetMapping(path = "/generate")
    public ResponseResult<CaptchaVo> generate() {
        CaptchaVo captchaVo = this.authCaptchaService.generate();
        return ResponseResult.ok(captchaVo);
    }

    /**
     * 是否开启登录/注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启登录/注册验证码（captchaType：login、register）")
    @GetMapping(path = "/{captchaType}/enabled")
    public ResponseResult<Boolean> enabled(@PathVariable String captchaType) {
        boolean enabled = this.authCaptchaService.captchaEnabled(CaptchaType.getByValue(captchaType));
        return ResponseResult.ok(enabled);
    }

}

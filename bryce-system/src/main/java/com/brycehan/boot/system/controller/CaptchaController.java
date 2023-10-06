package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.system.service.CaptchaService;
import com.brycehan.boot.system.vo.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "captcha", description = "验证码API")
@RequestMapping("/captcha")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    /**
     * 生成验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成验证码")
    @GetMapping(path = "/generate")
    public ResponseResult<CaptchaVo> generate() {
        CaptchaVo captchaVo = this.captchaService.generate();
        return ResponseResult.ok(captchaVo);
    }

    /**
     * 是否开启登录验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "是否开启登录验证码")
    @GetMapping(path = "/enabled")
    public ResponseResult<Boolean> enabled() {
        boolean enabled = this.captchaService.isCaptchaEnabled();
        return ResponseResult.ok(enabled);
    }

}

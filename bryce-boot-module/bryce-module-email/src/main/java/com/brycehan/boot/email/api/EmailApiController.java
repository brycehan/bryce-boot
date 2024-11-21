package com.brycehan.boot.email.api;


import com.brycehan.boot.api.email.dto.ToMailDto;
import com.brycehan.boot.api.email.dto.ToVerifyCodeEmailDto;
import com.brycehan.boot.common.enums.EmailType;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 邮件Api
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "邮件")
@RequestMapping(path = "/api/email")
@RestController
@RequiredArgsConstructor
public class EmailApiController {

    private final EmailService emailService;

    /**
     * 发送简单邮件
     *
     * @param toEmail 收邮件参数
     * @return 响应结果
     */
    @Operation(summary = "发送简单邮件")
    @PostMapping(path = "/sendSimpleEmail")
    public ResponseResult<Void> sendSimpleEmail(@Validated @RequestBody ToMailDto toEmail) {
        this.emailService.sendSimpleEmail(toEmail);
        return ResponseResult.ok();
    }

    /**
     * 发送附件邮件
     *
     * @param toEmail 收邮件参数
     * @param file 附件
     * @return 响应结果
     */
    @Operation(summary = "发送附件邮件")
    @PostMapping(path = "/sendHtmlEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<Void> sendHtmlEmail(@Validated ToMailDto toEmail, @RequestPart(required = false) MultipartFile[] file) {
        this.emailService.sendHtmlEmail(toEmail, file);
        return ResponseResult.ok();
    }

    /**
     * 发送验证码邮件
     *
     * @param toVerifyCodeEmailDto 收邮件参数
     * @param emailType 邮件类型
     * @return 是否发送成功
     */
    @Operation(summary = "发送验证码邮件")
    @PostMapping(path = "/sendValidateCode/{emailType}")
    public ResponseResult<Boolean> send(@Validated @RequestBody ToVerifyCodeEmailDto toVerifyCodeEmailDto, @PathVariable EmailType emailType) {
        this.emailService.send(toVerifyCodeEmailDto, emailType);
        return ResponseResult.ok(Boolean.TRUE);
    }

}

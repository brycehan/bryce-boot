package com.brycehan.boot.api.email;

import com.brycehan.boot.api.email.dto.ToMailDto;
import com.brycehan.boot.api.email.dto.ToVerifyCodeEmailDto;
import com.brycehan.boot.common.enums.EmailType;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 邮件Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface EmailApi {

    /**
     * 发送简单邮件
     *
     * @param toEmail 收邮件参数
     */
    @PostMapping(path = "/sendSimpleEmail")
    void sendSimpleEmail(@Validated @RequestBody ToMailDto toEmail);

    /**
     * 发送附件邮件
     *
     * @param toEmail 收邮件参数
     * @param file 附件
     */
    @PostMapping(path = "/sendHtmlEmail")
    void sendHtmlEmail(@Validated @RequestBody ToMailDto toEmail, MultipartFile[] file);

    /**
     * 发送验证码邮件
     *
     * @param toEmail 收邮件参数
     * @param emailType 邮件类型
     */
    @Operation(summary = "发送验证码邮件")
    @PostMapping(path = "/sendValidateCode/{emailType}")
    void send(@Validated @RequestBody ToVerifyCodeEmailDto toVerifyCodeEmailDto, @PathVariable EmailType emailType);

}

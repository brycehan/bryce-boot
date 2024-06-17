package com.brycehan.boot.api.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 邮件接收
 *
 * @author Bryce Han
 * @since 2024/6/15
 */
@Data
public class ToVerifyCodeEmailDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 收件人
     */
    @Schema(description = "收件人")
    @NotBlank(message = "收件人不能为空")
    @Size(min = 5, max = 100, message = "邮件收件人长度在5-100个字符")
    private String to;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @Length(min = 6, max = 6, message = "验证码长度为6")
    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;

}

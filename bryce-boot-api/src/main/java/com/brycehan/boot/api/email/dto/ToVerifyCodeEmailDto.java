package com.brycehan.boot.api.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Size(min = 1, max = 2000, message = "邮件收件人数在1-2000个")
    private String[] tos;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @Length(min = 6, max = 6, message = "验证码长度为6")
    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;

    /**
     * 抄送
     */
    @Schema(description = "抄送")
    @Size(max = 2000, message = "抄送收件人数最大2000个")
    private String[] cc;

    /**
     * 密送
     */
    @Schema(description = "密送")
    @Size(max = 2000, message = "密送收件人数最大2000个")
    private String[] bcc;

}

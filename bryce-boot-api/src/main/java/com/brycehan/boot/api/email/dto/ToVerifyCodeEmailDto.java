package com.brycehan.boot.api.email.dto;

import com.brycehan.boot.common.util.RegexPatterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Length(min = 5, max = 100)
    @Schema(description = "收件人")
    private String to;

    /**
     * 验证码
     */
    @NotEmpty
    @Pattern(regexp = RegexPatterns.VERIFY_CODE_REGEX)
    @Schema(description = "验证码")
    private String verifyCode;

}

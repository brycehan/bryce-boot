package com.brycehan.boot.api.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 邮件接收
 *
 * @author Bryce Han
 * @since 2024/6/15
 */
@Data
public class ToMailDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 收件人
     */
    @Size(min = 1, max = 2000)
    @Schema(description = "邮件收件人")
    private String[] tos;

    /**
     * 邮件主题
     */
    @NotBlank
    @Length(min = 2, max = 200)
    @Schema(description = "邮件主题")
    private String subject;

    /**
     * 邮件内容
     */
    @NotBlank
    @Length(message = "不能超过2G")
    @Schema(description = "邮件内容")
    private String content;

    /**
     * 邮件模板参数
     */
    @Schema(description = "邮件模板参数")
    private Map<String, Object> params;

    /**
     * 抄送
     */
    @Size(max = 2000)
    @Schema(description = "抄送")
    private String[] cc;

    /**
     * 密送
     */
    @Size(max = 2000)
    @Schema(description = "密送")
    private String[] bcc;

}

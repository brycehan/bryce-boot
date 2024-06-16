package com.brycehan.boot.api.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
public class ToMail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 收件人
     */
    @Schema(description = "收件人")
    @Size(min = 1, max = 2000, message = "邮件收件人数在1-2000个")
    private String[] tos;

    /**
     * 邮件主题
     */
    @Schema(description = "邮件主题")
    @Size(min = 2, max = 200, message = "邮件主题长度在2-200个字符")
    private String subject;

    /**
     * 邮件内容
     */
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

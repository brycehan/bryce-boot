package com.brycehan.boot.mp.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信公众号模版消息发送记录Vo
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号模版消息发送记录Vo")
public class MpTemplateMessageLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 用户openid
     */
    @Schema(description = "用户openid")
    private String toUser;

    /**
     * templateid
     */
    @Schema(description = "templateid")
    private String templateId;

    /**
     * 消息数据
     */
    @Schema(description = "消息数据")
    private String data;

    /**
     * 消息链接
     */
    @Schema(description = "消息链接")
    private String url;

    /**
     * 小程序信息
     */
    @Schema(description = "小程序信息")
    private String miniProgram;

    /**
     * 发送时间
     */
    @Schema(description = "发送时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime sendTime;

    /**
     * 发送结果
     */
    @Schema(description = "发送结果")
    private String sendResult;

}

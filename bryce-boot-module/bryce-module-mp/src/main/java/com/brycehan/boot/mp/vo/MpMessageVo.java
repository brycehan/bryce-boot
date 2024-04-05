package com.brycehan.boot.mp.vo;

import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 微信公众号消息 Vo
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号消息 Vo")
public class MpMessageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * openid
     */
    @Schema(description = "openid")
    private String openId;

    /**
     * 消息方向（1：in，0：out）
     */
    @Schema(description = "消息方向（1：in，0：out）")
    private Boolean inOut;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    private String messageType;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String content;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

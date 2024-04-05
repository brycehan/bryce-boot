package com.brycehan.boot.mp.vo;

import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 微信公众号带参二维码 Vo
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号带参二维码 Vo")
public class MpQrCodeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 是否为临时二维码
     */
    @Schema(description = "是否为临时二维码")
    private Boolean isTemporary;

    /**
     * 场景值ID
     */
    @Schema(description = "场景值ID")
    private String sceneStr;

    /**
     * 二维码ticket
     */
    @Schema(description = "二维码ticket")
    private String ticket;

    /**
     * 二维码图片解析后的地址
     */
    @Schema(description = "二维码图片解析后的地址")
    private String url;

    /**
     * 该二维码失效时间
     */
    @Schema(description = "该二维码失效时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime expireTime;

    /**
     * 该二维码创建时间
     */
    @Schema(description = "该二维码创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createTime;

}

package com.brycehan.boot.pay.vo;

import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 微信应用Vo
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Data
@Schema(description = "微信应用Vo")
public class WechatAppVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String appId;

    /**
     * 应用密钥
     */
    @Schema(description = "应用密钥")
    private String appSecret;

    /**
     * 类型（mp：微信公众号，ma：微信小程序）
     */
    @Schema(description = "类型（mp：微信公众号，ma：微信小程序）")
    private String type;

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirectUrl;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

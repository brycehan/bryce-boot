package com.brycehan.boot.system.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 系统租户Vo
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Data
@Schema(description = "系统租户Vo")
public class SysTenantVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    private String name;

    /**
     * 站点域名
     */
    @Schema(description = "站点域名")
    private String siteDomain;

    /**
     * 访问网址
     */
    @Schema(description = "访问网址")
    private String siteUrl;

    /**
     * logo
     */
    @Schema(description = "logo")
    private String siteLogo;

    /**
     * 系统配置
     */
    @Schema(description = "系统配置")
    private String config;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expiresTime;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

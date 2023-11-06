package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 系统租户Dto
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Data
@Schema(description = "系统租户Dto")
public class SysTenantDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 站点域名
     */
    @Schema(description = "站点域名")
    @Size(max = 200, groups = {SaveGroup.class, UpdateGroup.class})
    private String siteDomain;

    /**
     * 访问网址
     */
    @Schema(description = "访问网址")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String siteUrl;

    /**
     * logo
     */
    @Schema(description = "logo")
    @Size(max = 200, groups = {SaveGroup.class, UpdateGroup.class})
    private String siteLogo;

    /**
     * 系统配置
     */
    @Schema(description = "系统配置")
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String config;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private LocalDate expiresTime;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

}

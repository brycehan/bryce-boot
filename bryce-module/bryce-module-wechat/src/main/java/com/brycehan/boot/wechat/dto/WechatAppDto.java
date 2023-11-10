package com.brycehan.boot.wechat.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 微信应用Dto
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Data
@Schema(description = "微信应用Dto")
public class WechatAppDto implements Serializable {

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
     * 名称
     */
    @Schema(description = "名称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String appId;

    /**
     * 应用密钥
     */
    @Schema(description = "应用密钥")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String appSecret;

    /**
     * 类型（mp：微信公众号，ma：微信小程序）
     */
    @Schema(description = "类型（mp：微信公众号，ma：微信小程序）")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String token;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

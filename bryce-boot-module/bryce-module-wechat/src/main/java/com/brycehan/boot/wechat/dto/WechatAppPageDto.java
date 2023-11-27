package com.brycehan.boot.wechat.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信应用PageDto
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "微信应用PageDto")
public class WechatAppPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @Size(max = 50)
    private String name;

    /**
     * 类型（mp：微信公众号，ma：微信小程序）
     */
    @Schema(description = "类型（mp：微信公众号，ma：微信小程序）")
    @Size(max = 10)
    private String type;

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

}

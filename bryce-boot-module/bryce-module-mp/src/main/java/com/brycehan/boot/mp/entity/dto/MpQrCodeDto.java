package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信公众号带参二维码Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号带参二维码Dto")
public class MpQrCodeDto implements Serializable {

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
     * 是否为临时二维码
     */
    @Schema(description = "是否为临时二维码")
    private Boolean isTemporary;

    /**
     * 场景值ID
     */
    @Schema(description = "场景值ID")
    @NotEmpty(message = "场景值ID不能为空")
    @Size(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String sceneStr;

    /**
     * 二维码ticket
     */
    @Schema(description = "二维码ticket")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String ticket;

    /**
     * 二维码图片解析后的地址
     */
    @Schema(description = "二维码图片解析后的地址")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 该二维码失效时间，过期时间不能超过30天
     */
    @Schema(description = "该二维码失效时间，过期时间不能超过30天")
    @Max(value = 2592000, message = "过期时间不能超过30天（单位秒）")
    private Integer expireTimeSeconds;

}

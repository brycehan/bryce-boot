package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信公众号粉丝PageDto
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号粉丝PageDto")
@EqualsAndHashCode(callSuper = true)
public class MpUserPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Size(max = 50)
    private String nickname;

    /**
     * 城市
     */
    @Schema(description = "城市")
    @Size(max = 20)
    private String city;

    /**
     * 扫码场景值
     */
    @Schema(description = "扫码场景值")
    @Size(max = 64)
    private String qrSceneStr;

}

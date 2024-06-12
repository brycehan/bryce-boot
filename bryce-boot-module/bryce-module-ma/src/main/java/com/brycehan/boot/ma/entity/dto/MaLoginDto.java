package com.brycehan.boot.ma.entity.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信小程序登录Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信小程序登录Dto")
public class MaLoginDto extends BaseDto {

    /**
     * 小程序登录返回的code
     */
    @Schema(description = "小程序登录返回的code")
    @NotEmpty(message = "code不能为空")
    @Size(max = 64)
    private String code;

    /**
     * 小程序登录返回的encryptedData
     */
    @Schema(description = "小程序登录返回的encryptedData")
    @Size(max = 255)
    private String encryptedData;

    /**
     * 小程序登录返回的iv
     */
    @Schema(description = "小程序登录返回的iv")
    @Size(max = 255)
    private String iv;

}

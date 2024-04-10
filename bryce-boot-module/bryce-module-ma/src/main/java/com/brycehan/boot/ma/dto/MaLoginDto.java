package com.brycehan.boot.ma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信小程序登录Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信小程序登录Dto")
public class MaLoginDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

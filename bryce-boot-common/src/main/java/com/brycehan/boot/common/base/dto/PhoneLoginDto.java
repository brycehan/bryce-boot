package com.brycehan.boot.common.base.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 手机号登录Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Schema(description = "手机号登录Dto")
public class PhoneLoginDto extends BaseDto {

    /**
     * 手机号
     */
    @NotNull
    @NotBlank
    @Size(min = 11, max = 20)
    @Schema(description = "手机号")
    private String phone;

    /**
     * 验证码
     */
    @NotNull
    @NotBlank
    @Size(max = 8)
    @Schema(description = "验证码")
    private String code;

}

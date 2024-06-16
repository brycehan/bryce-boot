package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Jwt令牌Dto
 *
 * @since 2022/5/18
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Jwt令牌Dto")
public class JwtTokenDto extends BaseDto {
    /**
     * jwt令牌
     */
    @NotNull
    @Schema(description = "jwt令牌")
    private String token;
}

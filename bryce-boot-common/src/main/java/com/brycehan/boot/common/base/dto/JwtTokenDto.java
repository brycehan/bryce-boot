package com.brycehan.boot.common.base.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Jwt令牌Dto
 *
 * @since 2022/5/18
 * @author Bryce Han
 */
@Schema(description = "Jwt令牌Dto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtTokenDto extends BaseDto {
    /**
     * jwt令牌
     */
    @NotNull
    @Schema(description = "jwt令牌")
    private String token;
}

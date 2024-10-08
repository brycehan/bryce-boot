package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.util.RegexPatterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号登录Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账号登录Dto")
public class AccountLoginDto extends BaseDto {

    /**
     * 账号
     */
    @NotNull
    @Size(min = 2, max = 50)
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Size(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH)
    @Schema(description = "密码")
    private String password;

    /**
     * key
     */
    @Size(max = 36)
    @Schema(description = "key")
    private String key;

    /**
     * 验证码
     */
    @Size(max = 6)
    @Schema(description = "验证码")
    private String code;

}

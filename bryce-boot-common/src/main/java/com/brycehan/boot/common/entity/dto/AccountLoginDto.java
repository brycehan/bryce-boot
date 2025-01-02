package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 2, max = 50)
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH)
    @Schema(description = "密码")
    private String password;

    /**
     * key
     */
    @Length(max = 36)
    @Schema(description = "key")
    private String key;

    /**
     * 验证码
     */
    @Length(max = 6)
    @Schema(description = "验证码")
    private String code;

    /**
     * 记住我
     */
    @Schema(description = "记住我")
    private Boolean rememberMe = Boolean.FALSE;
}

package com.brycehan.boot.common.base.dto;

import com.brycehan.boot.common.constant.UserConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 登录Dto
 *
 * @author Bryce Han
 * @since 2022/5/10
 */
@Data
@Schema(description = "登录Dto")
public class LoginDto {

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
     * uuid
     */
    @Size(max = 36)
    @Schema(description = "uuid")
    private String uuid;

    /**
     * 验证码
     */
    @Size(max = 8)
    @Schema(description = "验证码")
    private String code;

    /**
     * 登录类型
     */
    @Pattern(regexp = "^account|mobile$", message = "登录类型只能是account、mobile")
    @Schema(description = "登录类型")
    private String type;

    // mobile captcha autoLogin
    // {"username":"adf","password":"adf","autoLogin":true,"type":"account"}
//    {"autoLogin":true,"mobile":"15853155402","captcha":"sfsd","type":"mobile"}
}

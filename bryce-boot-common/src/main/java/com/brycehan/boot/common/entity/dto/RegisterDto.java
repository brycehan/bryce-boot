package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.constant.UserConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注册Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "注册Dto")
public class RegisterDto extends BaseDto {

    /**
     * 账号
     */
    @NotEmpty
    @Size(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH)
    @Schema(description = "账号")
    private String username;

    /**
     * 姓名
     */
    @Size(max = 50)
    @Schema(description = "姓名")
    private String nickname;

    /**
     * 密码
     */
    @NotEmpty
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
     * 手机号码
     */
    @Size(max = 20)
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 验证码
     */
    @Size(max = 6)
    @Schema(description = "验证码")
    private String code;

}

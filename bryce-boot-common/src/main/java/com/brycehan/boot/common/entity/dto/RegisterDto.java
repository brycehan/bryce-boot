package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH)
    @Schema(description = "账号")
    private String username;

    /**
     * 姓名
     */
    @Length(max = 50)
    @Schema(description = "姓名")
    private String nickname;

    /**
     * 密码
     */
    @NotEmpty
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
     * 手机号码
     */
    @Length(max = 20)
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 验证码
     */
    @Length(max = 6)
    @Schema(description = "验证码")
    private String code;

}

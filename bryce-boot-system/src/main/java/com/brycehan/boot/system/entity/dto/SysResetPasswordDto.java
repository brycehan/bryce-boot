package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统用户重置密码Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户重置密码Dto")
public class SysResetPasswordDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull
    private Long id;

    /**
     * 新密码
     */
    @Length(min = 6, max = 20)
    @Pattern(regexp = "^[^\\s\\u4e00-\\u9fa5]*$", message = "不允许有空格、中文")
    @Schema(description = "新密码")
    private String password;

}

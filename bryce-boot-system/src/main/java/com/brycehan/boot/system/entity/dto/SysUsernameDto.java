package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户账号Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户手机号码Dto")
public class SysUsernameDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    @Size(min = 1, max = 50, message = "账号长度在1-50个字符")
    private String username;

}

package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统岗位编码Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统岗位编码Dto")
public class SysPostCodeDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    @Size(min = 2, max = 30,  message = "岗位编码长度在2-50个字符")
    private String code;

}

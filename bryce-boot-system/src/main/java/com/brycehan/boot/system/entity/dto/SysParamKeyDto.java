package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统参数键名Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统参数键名Dto")
public class SysParamKeyDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    @NotBlank(message = "参数键名不能为空")
    @Size(min = 2, max = 100, message = "参数键名长度在2-100个字符")
    private String paramKey;

}

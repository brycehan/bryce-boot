package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统字典类型编码Dto
 *
 * @since 2022/5/14
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统字典类型编码Dto")
public class SysDictTypeCodeDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典类型
     */
    @NotBlank
    @Length(max = 100)
    @Schema(description = "字典类型")
    private String dictType;

}

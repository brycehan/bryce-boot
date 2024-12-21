package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 地区编码PageDto
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Data
@Schema(description = "地区编码PageDto")
@EqualsAndHashCode(callSuper = true)
public class SysAreaCodePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @Length(max = 255)
    private String name;

}

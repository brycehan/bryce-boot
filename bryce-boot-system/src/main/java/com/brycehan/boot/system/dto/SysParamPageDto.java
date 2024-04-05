package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统参数PageDto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统参数PageDto")
public class SysParamPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    @Size(max = 100)
    private String paramName;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    @Size(max = 100)
    private String paramKey;

    /**
     * 参数类型（built_in：内置，system：系统）
     */
    @Schema(description = "参数类型（built_in：内置，system：系统）")
    private String paramType;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createdTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createdTimeEnd;

}

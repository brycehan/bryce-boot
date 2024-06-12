package com.brycehan.boot.api.system.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统参数Vo
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@Schema(description = "系统参数Vo")
public class SysParamVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String paramName;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    private String paramKey;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    private String paramValue;

    /**
     * 参数类型（built_in：内置，system：系统）
     */
    @Schema(description = "参数类型（built_in：内置，system：系统）")
    private String paramType;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

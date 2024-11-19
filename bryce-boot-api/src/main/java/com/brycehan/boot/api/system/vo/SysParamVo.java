package com.brycehan.boot.api.system.vo;

import com.brycehan.boot.common.enums.ParamType;
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
     * 参数类型（0：内置，0：应用）
     */
    @Schema(description = "参数类型（0：内置，0：应用）")
    private ParamType paramType;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

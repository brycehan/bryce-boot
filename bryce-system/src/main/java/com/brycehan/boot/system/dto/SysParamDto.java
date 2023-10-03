package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统参数Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@Schema(description = "系统参数Dto")
public class SysParamDto implements Serializable {

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
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String paramName;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String paramKey;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String paramValue;

    /**
     * 是否系统内置（Y：是，N：否）
     */
    @Schema(description = "是否系统内置（Y：是，N：否）")
    private String builtIn;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}

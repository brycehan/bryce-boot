package com.brycehan.boot.api.system.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统参数Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统参数Dto")
public class SysParamApiDto extends BaseDto {

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
     * 参数类型（built_in：内置，system：系统）
     */
    @Schema(description = "参数类型（built_in：内置，system：系统）")
    private String paramType;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

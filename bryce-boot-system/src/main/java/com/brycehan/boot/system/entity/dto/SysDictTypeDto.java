package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统字典类型Dto
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统字典类型Dto")
public class SysDictTypeDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictType;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（false：停用，true：正常）
     */
    @Schema(description = "状态（false：停用，true：正常）")
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

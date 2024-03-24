package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统字典类型Dto
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Data
@Schema(description = "系统字典类型Dto")
public class SysDictTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

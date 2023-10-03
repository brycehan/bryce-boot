package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统字典数据Dto
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Schema(description = "系统字典数据Dto")
@Data
public class SysDictDataDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictLabel;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String dictValue;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private Long dictTypeId;

    /**
     * 标签属性
     */
    @Schema(description = "标签属性")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String labelClass;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

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

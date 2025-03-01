package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import org.hibernate.validator.constraints.Length;
import com.brycehan.boot.common.enums.StatusType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类Dto
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程分类Dto")
public class BpmCategoryDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 分类标志
     */
    @Schema(description = "分类标志")
    @Length(max = 30, groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 分类描述
     */
    @Schema(description = "分类描述")
    @Length(max = 300, groups = {SaveGroup.class, UpdateGroup.class})
    private String description;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

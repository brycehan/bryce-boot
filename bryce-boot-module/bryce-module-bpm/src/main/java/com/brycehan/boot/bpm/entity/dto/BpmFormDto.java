package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 表单定义Dto
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "表单定义Dto")
public class BpmFormDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 表单名
     */
    @Schema(description = "表单名")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 表单的配置
     */
    @Schema(description = "表单的配置")
    @Length(max = 1000, groups = {SaveGroup.class, UpdateGroup.class})
    private String conf;

    /**
     * 表单项的数组
     */
    @Schema(description = "表单项的数组")
    private List<String> fields;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

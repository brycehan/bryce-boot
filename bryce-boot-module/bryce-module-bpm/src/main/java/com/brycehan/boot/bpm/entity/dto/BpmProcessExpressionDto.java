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
 * 流程表达式Dto
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程表达式Dto")
public class BpmProcessExpressionDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 流程实例的名称
     */
    @Schema(description = "流程实例的名称")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 流程实例的状态
     */
    @Schema(description = "流程实例的状态")
    private StatusType status;

    /**
     * 表达式
     */
    @Schema(description = "表达式")
    @Length(max = 5000, groups = {SaveGroup.class, UpdateGroup.class})
    private String expression;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

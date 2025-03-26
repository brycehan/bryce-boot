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
 * 流程监听器 Dto
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程监听器Dto")
public class BpmProcessListenerDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 监听器名称
     */
    @Schema(description = "监听器名称")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private StatusType status;

    /**
     * 监听类型
     */
    @Schema(description = "监听类型")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 监听事件
     */
    @Schema(description = "监听事件")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String event;

    /**
     * 值类型
     */
    @Schema(description = "值类型")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String valueType;

    /**
     * 值
     */
    @Schema(description = "值")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String value;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

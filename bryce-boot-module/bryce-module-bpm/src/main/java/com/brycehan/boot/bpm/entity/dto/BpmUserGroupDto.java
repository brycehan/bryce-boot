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

import java.util.List;

/**
 * 用户组Dto
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户组Dto")
public class BpmUserGroupDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 组名
     */
    @Schema(description = "组名")
    @Length(max = 30, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String description;

    /**
     * 成员编号数组
     */
    @Schema(description = "成员编号数组")
    private List<String> userIds;

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

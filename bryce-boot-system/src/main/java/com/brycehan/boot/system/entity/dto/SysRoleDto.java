package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 系统角色Dto
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统角色Dto")
public class SysRoleDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[A-Z_]+$", message = "必须是大写字母或下划线", groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 数据范围（0：全部数据，1：自定义数据，2：本部门及以下部门数据，3：本部门数据，4：本人数据）
     */
    @Schema(description = "数据范围（0：全部数据，1：自定义数据，2：本部门及以下部门数据，3：本部门数据，4：本人数据）")
    private DataScopeType dataScope;

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
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门IDs
     */
    @Schema(description = "部门IDs")
    private List<Long> deptIds;

    /**
     * 菜单IDs
     */
    @Schema(description = "菜单IDs")
    private List<Long> menuIds;

}

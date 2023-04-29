package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.boot.common.base.entity.BasePo;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统角色
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@TableName("brc_sys_role")
@Schema(description = "SysRole实体")
public class SysRole extends BasePo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = AddGroup.class)
    @NotNull(groups = UpdateGroup.class)
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String roleName;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String roleCode;

    /**
     * 数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）
     */
    @Schema(description = "数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）")
    private Boolean dataScope;

    /**
     * 菜单树父子选择项是否关联显示（1：是，0：否）
     */
    @Schema(description = "菜单树父子选择项是否关联显示（1：是，0：否）")
    private Boolean menuAssociationDisplayed;

    /**
     * 部门树父子选择项是否关联显示（1：是，0：否）
     */
    @Schema(description = "部门树父子选择项是否关联显示（1：是，0：否）")
    private Boolean deptAssociationDisplayed;

    /**
     * 状态（0：正式数据，1：删除）
     */
    @Schema(description = "状态（0：正式数据，1：删除）")
    private Boolean deleteFlag;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Integer status;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建人账号
     */
    @Schema(description = "创建人账号")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

}

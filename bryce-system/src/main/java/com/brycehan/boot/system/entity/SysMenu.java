package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.boot.common.base.entity.BaseEntity;
import com.brycehan.boot.common.validator.AddGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单实体
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@TableName("brc_sys_menu")
@Schema(description = "SysMenu实体")
public class SysMenu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Null(groups = AddGroup.class)
    @NotNull(groups = UpdateGroup.class)
    @Schema(description = "ID")
    @TableId(value = "id")
    private Long id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 类型（M：菜单，B：按钮）
     */
    @Schema(description = "类型（M：菜单，B：按钮）")
    @Size(max = 1, groups = {AddGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Schema(description = "父菜单ID，一级菜单为0")
    private String parentId;

    /**
     * 组件路径
     */
    private String url;

    /**
     * 权限标识
     */
    private String authority;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 打开方式（0：内部，1：外部）
     */
    private Integer openStyle;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（0：停用，1：正常）
     */
    private Integer status;

    /**
     * 状态（0：正式数据，1：删除）
     */
    private Boolean deleted;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private Long createdUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 子菜单列表
     */
    @Schema(hidden = true)
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

}

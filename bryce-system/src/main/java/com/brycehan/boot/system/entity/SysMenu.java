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
public class SysMenu extends BasePo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Null(groups = AddGroup.class)
    @NotNull(groups = UpdateGroup.class)
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String menuName;

    /**
     * 类型（D：目录，M：菜单，B：按钮）
     */
    @Schema(description = "类型（D：目录，M：菜单，B：按钮）")
    @Size(max = 1, groups = {AddGroup.class, UpdateGroup.class})
    private String menuType;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Schema(description = "父菜单ID，一级菜单为0")
    private String parentId;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String icon;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    @Size(max = 200, groups = {AddGroup.class, UpdateGroup.class})
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String component;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String query;

    /**
     * 是否为外链（0：否，1：是）
     */
    @Schema(description = "是否为外链（0：否，1：是）")
    private Boolean isFrame;

    /**
     * 是否缓存（0：否，1：是）
     */
    @Schema(description = "是否缓存（0：否，1：是）")
    private Boolean isCache;

    /**
     * 菜单状态（0：隐藏，1：显示）
     */
    @Schema(description = "菜单状态（0：隐藏，1：显示）")
    private Boolean visible;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String permission;

    /**
     * 状态（0：正式数据，1：删除）
     */
    @Schema(description = "状态（0：正式数据，1：删除）")
    private Boolean deleteFlag;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sortNumber;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
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
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 子菜单列表
     */
    @Schema(hidden = true)
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

}

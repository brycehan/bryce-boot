package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.group.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 系统菜单分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@Schema(description = "SysMenuPageDto对象")
public class SysMenuPageDto extends BasePageDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 菜单名称
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 类型（D：目录，M：菜单，B：按钮）
     */
    @Size(max = 1, groups = QueryGroup.class)
    @Schema(description = "类型（D：目录，M：菜单，B：按钮）")
    private String menuType;

    /**
     * 父菜单ID，一级菜单为0
     */
    @Schema(description = "父菜单ID，一级菜单为0")
    private String parentId;

    /**
     * 菜单图标
     */
    @Size(max = 100, groups = QueryGroup.class)
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 路由地址
     */
    @Size(max = 200, groups = QueryGroup.class)
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "路由参数")
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
    @Size(max = 100, groups = QueryGroup.class)
    @Schema(description = "权限标识")
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
    private String createUserId;

    /**
     * 创建人账号
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "创建人账号")
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private String updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Size(max = 500, groups = QueryGroup.class)
    @Schema(description = "备注")
    private String remark;

}

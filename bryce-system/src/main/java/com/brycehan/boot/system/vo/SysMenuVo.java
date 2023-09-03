package com.brycehan.boot.system.vo;

import com.brycehan.boot.common.util.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 系统菜单数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Data
@Schema(description = "系统菜单Vo")
public class SysMenuVo extends TreeNode<SysMenuVo> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 类型（D：目录，M：菜单，B：按钮）
     */
    @Schema(description = "菜单类型（D：目录，M：菜单，B：按钮）")
    private String type;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String url;

    /**
     * 路由参数
     */
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
    @Schema(description = "权限标识")
    private String authority;

    /**
     * 状态（0：正式数据，1：删除）
     */
    @Schema(description = "状态（0：正式数据，1：删除）")
    private Boolean deleted;

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
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}

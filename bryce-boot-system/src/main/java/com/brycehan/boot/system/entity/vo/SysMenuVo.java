package com.brycehan.boot.system.entity.vo;

import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.enums.VisibleType;
import com.brycehan.boot.common.util.TreeNode;
import com.brycehan.boot.system.common.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统菜单Vo
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
     * 菜单类型（C：目录，M：菜单，B：按钮）
     */
    @Schema(description = "菜单类型")
    private MenuType type;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String url;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String authority;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 打开方式（0：内部，1：外部）
     */
    @Schema(description = "打开方式（0：内部，1：外部）")
    private Boolean openStyle;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 可见性类型
     */
    @Schema(description = "可见性类型")
    private VisibleType visible;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

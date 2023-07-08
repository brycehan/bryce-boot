package com.brycehan.boot.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.brycehan.boot.common.base.entity.BasePo;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Schema(description = "系统菜单Dto")
@Getter
@Setter
public class SysMenuDto extends BasePo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = AddGroup.class)
    @NotNull(groups = UpdateGroup.class)
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
    @Schema(description = "菜单类型（D：目录，M：菜单，B：按钮）")
    @Pattern(regexp = "^[DMB]$", groups = {AddGroup.class, UpdateGroup.class}, message = "菜单类型值只能是D、M或B")
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
    @Range(max = 2147483647, groups = {AddGroup.class, UpdateGroup.class})
    private Integer sortNumber;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @Range(max = 1, message = "状态值只能是0或1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

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
    private List<SysMenuDto> children = new ArrayList<>();

}

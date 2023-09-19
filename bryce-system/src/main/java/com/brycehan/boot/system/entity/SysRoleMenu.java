package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统角色菜单关系entity
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("brc_sys_role_menu")
public class SysRoleMenu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long menuId;

}

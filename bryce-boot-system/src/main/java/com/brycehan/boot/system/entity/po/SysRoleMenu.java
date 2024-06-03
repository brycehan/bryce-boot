package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统角色菜单关系entity
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

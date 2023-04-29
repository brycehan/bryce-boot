package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统角色菜单中间表
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@TableName("brc_sys_role_menu")
@Schema(description = "SysRoleMenu实体")
public class SysRoleMenu extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private String roleId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private String menuId;

}

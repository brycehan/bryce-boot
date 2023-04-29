package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.system.entity.SysRoleMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统角色菜单中间表分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@Schema(description = "SysRoleMenuPageDto对象")
public class SysRoleMenuPageDto extends BasePageDto {

    private static final long serialVersionUID = 1L;

    /**
     * 系统角色菜单中间表实体
     */
    @Schema(description = "系统角色菜单中间表实体")
    private SysRoleMenu sysRoleMenu;

}

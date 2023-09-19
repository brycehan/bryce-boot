package com.brycehan.boot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统角色用户PageDto
 *
 * @author Bryce Han
 * @since 2023/09/11
 */
@Data
@Schema(description = "系统角色用户PageDto")
@EqualsAndHashCode(callSuper = true)
public class SysRoleUserPageDto extends SysUserPageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

}

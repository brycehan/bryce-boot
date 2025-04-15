package com.brycehan.boot.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 系统用户权限 Vo
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
@Schema(description = "系统用户权限 Vo")
public class SysUserPermissionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色编码集合
     */
    @Schema(description = "角色编码集合")
    private Set<String> roleSet;

    /**
     * 权限集合
     */
    @Schema(description = "权限集合")
    private Set<String> authoritySet;

}

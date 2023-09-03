package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户角色中间表
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@TableName("brc_sys_user_role")
@Schema(description = "SysUserRole实体")
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

}

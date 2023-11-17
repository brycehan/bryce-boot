package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统角色数据范围entity
 *
 * @since 2023/09/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("brc_sys_role_data_scope")
public class SysRoleDataScope extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 机构ID
     */
    private Long orgId;

}

package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.common.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Optional;
import java.util.Set;

/**
 * 系统角色entity
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_role")
public class SysRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 数据范围（0：全部数据，1：自定义数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据）
     */
    private DataScopeType dataScope;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 机构ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long orgId;

    /**
     * 角色菜单权限
     */
    @TableField(exist = false)
    private Set<String> authoritySet;

    public SysRole(Long id) {
        super.setId(id);
    }

    /**
     * 是否为超级管理员
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin(this);
    }

    /**
     * 是否为超级管理员
     */
    public static boolean isSuperAdmin(SysRole sysRole) {
        return Optional.ofNullable(sysRole).map(SysRole::getId).map(id -> id == 1L).orElse(false);
    }
}

package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.entity.vo.RoleVo;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * 系统用户entity
 *
 * @author Bryce Han
 * @since 2022/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_user")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别（M：男，F：女，N：未知）
     */
    private GenderType gender;

    /**
     * 用户类型（0：系统用户）
     */
    private Integer type;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 职业
     */
    private String profession;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账号锁定状态（0：锁定，1：正常）
     */
    private Boolean accountNonLocked;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 拥有角色集合
     */
    @TableField(exist = false)
    private Set<RoleVo> roles;

    /**
     * 拥有角色编码集合
     */
    @TableField(exist = false)
    private Set<String> roleSet;

    /**
     * 权限
     */
    @TableField(exist = false)
    private Set<String> authoritySet;

    /**
     * 是否具有某个角色
     *
     * @param roleKey 角色key
     * @return 是否具有某个角色布尔值
     */
    @SuppressWarnings("unused")
    public boolean hasRole(String roleKey) {
        return !CollectionUtils.isEmpty(roleSet) && roleSet.contains(roleKey);
    }

    /**
     * 是否是超级管理员
     *
     * @return 是否是超级管理员布尔值
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin(this);
    }

    /**
     * 是否是超级管理员
     *
     * @param sysUser 用户
     * @return 是否是超级管理员布尔值
     */
    public static boolean isSuperAdmin(SysUser sysUser) {
        return sysUser != null && Objects.equals(sysUser.getId(), 1L);
    }

    public static SysUser of(LoginUser loginUser) {
        return SysUserConvert.INSTANCE.convertSysUser(loginUser);
    }

    public static SysUser of(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        return sysUser;
    }
}

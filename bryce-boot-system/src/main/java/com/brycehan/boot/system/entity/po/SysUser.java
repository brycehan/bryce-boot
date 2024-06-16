package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 性别（M：男, F：女）
     */
    private String gender;

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
     * 机构ID
     */
    private Long orgId;

    /**
     * 超级管理员
     */
    private Boolean superAdmin;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

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
     * 角色
     */
    @Schema(description = "角色")
    @TableField(exist = false)
    private Set<String> roles;

    /**
     * 权限
     */
    @Schema(description = "权限")
    @TableField(exist = false)
    private Set<String> authoritySet;

    /**
     * 是否具有某个角色
     *
     * @param roleKey 角色key
     * @return 是否具有某个角色布尔值
     */
    public boolean hasRole(String roleKey) {
        return !CollectionUtils.isEmpty(roles) && roles.contains(roleKey);
    }

}

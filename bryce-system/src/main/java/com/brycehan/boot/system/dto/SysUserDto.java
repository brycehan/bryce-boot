package com.brycehan.boot.system.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.boot.common.validator.AddGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统用户Dto
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Data
@Schema(description = "系统用户Dto")
public class SysUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String password;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String fullName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String avatar;

    /**
     * 性别（M：男, F：女，N：未知）
     */
    @Schema(description = "性别（M：男, F：女，N：未知）")
    @Size(max = 1, groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[MFN]$", groups = {AddGroup.class, UpdateGroup.class}, message = "性别值只能是M、F、N")
    private String gender;

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Boolean type;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @Size(max = 20, groups = {AddGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long orgId;

    /**
     * 超级管理员
     */
    @Schema(description = "超级管理员")
    private Boolean superAdmin;

    /**
     * 租户管理员
     */
    @Schema(description = "租户管理员")
    private Boolean tenantAdmin;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 账号锁定状态（0：锁定，1：正常）
     */
    @Schema(description = "账号锁定状态（0：锁定，1：正常）")
    private Boolean accountNonLocked;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @Size(max = 128, groups = {AddGroup.class, UpdateGroup.class})
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}

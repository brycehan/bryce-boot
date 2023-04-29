package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.boot.common.base.entity.BasePo;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 系统用户实体
 *
 * @author Bryce Han
 * @since 2022/5/8
 */
@Schema(description = "SysUser实体")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("brc_sys_user")
public class SysUser extends BasePo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Null(groups = AddGroup.class)
    @NotNull(groups = UpdateGroup.class)
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 账号
     */
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "密码")
    private String password;

    /**
     * 姓名
     */
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "姓名")
    private String fullName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String avatar;

    /**
     * 性别（M：男, F：女）
     */
    @Schema(description = "性别（M：男, F：女）")
    @Size(max = 1, groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[MF]$", groups = {AddGroup.class, UpdateGroup.class}, message = "性别值只能是M或F")
    private String gender;

    // address country国家 geographic位置 group

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Boolean userType;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private String deptId;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Integer status;

    /**
     * 删除标志（0：存在，1：删除）
     */
    @Schema(description = "删除标志（0：存在，1：删除）")
    private Boolean deleteFlag;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建人账号
     */
    @Schema(description = "创建人账号")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

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
    private Set<String> permissions;

    /**
     * 是否是管理员
     *
     * @return 是否是管理员
     */
    public boolean isAdmin() {
        return !CollectionUtils.isEmpty(roles) && roles.contains("ROLE_ADMIN");
    }

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

package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.group.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统用户分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/14
 */
@Getter
@Setter
@Schema(description = "SysUserPageDto对象")
public class SysUserPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 账号
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "密码")
    private String password;

    /**
     * 姓名
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "姓名")
    private String fullName;

    /**
     * 头像地址
     */
    @Size(max = 100, groups = QueryGroup.class)
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 性别（M：男, F：女）
     */
    @Size(max = 1, groups = QueryGroup.class)
    @Schema(description = "性别（M：男, F：女）")
    private String gender;

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
    @Size(max = 20, groups = QueryGroup.class)
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "邮箱")
    private String email;

    /**
     * 账号锁定状态（0：锁定，1：正常）
     */
    @Schema(description = "账号锁定状态（0：锁定，1：正常）")
    private Boolean accountNonLocked;

    /**
     * 最后登录IP
     */
    @Size(max = 128, groups = QueryGroup.class)
    @Schema(description = "最后登录IP")
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
    private String createUserId;

    /**
     * 创建人账号
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "创建人账号")
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private String updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Size(max = 500, groups = QueryGroup.class)
    @Schema(description = "备注")
    private String remark;

}

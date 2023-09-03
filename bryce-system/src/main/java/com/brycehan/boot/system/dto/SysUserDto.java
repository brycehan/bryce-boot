package com.brycehan.boot.system.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("brc_sys_user")
@Schema(description = "系统用户Dto")
public class SysUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String fullName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 性别（M：男, F：女）
     */
    @Schema(description = "性别（M：男, F：女）")
    private String gender;

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Integer type;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private String orgId;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
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
    @Schema(description = "最后登录IP")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
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
     * 删除标识（0：存在，1：删除）
     */
    @Schema(description = "删除标识（0：存在，1：删除）")
    private Boolean deleted;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createdUserId;

    /**
     * 创建人账号
     */
    @Schema(description = "创建人账号")
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updatedUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}

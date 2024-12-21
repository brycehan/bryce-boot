package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户Dto
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统用户Dto")
public class SysUserDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @Length(max = 0, groups = {UpdateGroup.class}, message = "不能在些修改密码")
    @Length(min = 6, max = 20, groups = SaveGroup.class)
    @Pattern(regexp = "^[^\\s\\u4e00-\\u9fa5]*$", groups = SaveGroup.class, message = "不允许有空格、中文")
    private String password;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String nickname;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String avatar;

    /**
     * 性别（M：男，F：女，N：未知）
     */
    @Schema(description = "性别（M：男，F：女，N：未知）")
    private GenderType gender;

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Integer type;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @Length(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Length(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 角色IDs
     */
    @Schema(description = "角色IDs")
    private List<Long> roleIds;

    /**
     * 岗位IDs
     */
    @Schema(description = "岗位ID")
    private List<Long> postIds;

}

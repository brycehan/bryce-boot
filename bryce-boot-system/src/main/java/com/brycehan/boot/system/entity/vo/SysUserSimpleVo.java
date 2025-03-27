package com.brycehan.boot.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户精简信息 Vo
 *
 * @since 2025/3/11
 * @author Bryce Han
 */
@Data
@Schema(description = "用户精简信息 Vo")
public class SysUserSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 部门编号
     */
    @Schema(description = "部门编号")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

}
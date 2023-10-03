package com.brycehan.boot.system.vo;

import com.brycehan.boot.common.util.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统机构Vo
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Schema(description = "系统机构Vo")
@Data
@EqualsAndHashCode(callSuper=false)
public class SysOrgVo extends TreeNode<SysOrgVo> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String name;

    /**
     * 机构编码
     */
    @Schema(description = "机构编码")
    private String code;

    /**
     * 祖级机构列表
     */
    @Schema(description = "祖级机构列表")
    private String ancestor;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String contactNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}

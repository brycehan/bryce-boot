package com.brycehan.boot.system.entity.vo;

import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.util.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统部门Vo
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统部门Vo")
public class SysDeptVo extends TreeNode<SysDeptVo> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String name;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String code;

    /**
     * 上级部门名称
     */
    @Schema(description = "上级部门名称")
    private String parentName;

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
    private StatusType status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}

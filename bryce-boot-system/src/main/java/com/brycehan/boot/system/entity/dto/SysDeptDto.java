package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.util.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 系统部门 Dto
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统部门 Dto")
public class SysDeptDto extends TreeNode<SysDeptDto> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @NotBlank(groups = {SaveGroup.class, UpdateGroup.class})
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    @Length(max = 30, groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 祖级部门列表
     */
    @Schema(description = "祖级部门列表")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String ancestor;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    private Long leaderUserId;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @Length(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String contactNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @Min(value = 0)
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

}

package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.util.TreeNode;
import com.brycehan.boot.common.validator.AddGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统机构Dto
 *
 * @author Bryce Han
 * @since 2023/08/31
 */
@Schema(description = "系统机构Dto")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOrgDto extends TreeNode<SysOrgDto> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class}, message = "机构名称不能为空")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 机构编码
     */
    @Schema(description = "机构编码")
    @Size(max = 30, groups = {AddGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 祖级机构列表
     */
    @Schema(description = "祖级机构列表")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String ancestor;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String leader;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @Size(max = 20, groups = {AddGroup.class, UpdateGroup.class})
    private String contactNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @Min(value = 0, message = "排序值不能小于0")
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

}

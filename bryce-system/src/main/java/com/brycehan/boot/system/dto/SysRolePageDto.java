package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.group.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 系统角色分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Getter
@Setter
@Schema(description = "SysRolePageDto对象")
public class SysRolePageDto extends BasePageDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;
    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Size(max = 50, groups = QueryGroup.class)
    private String roleName;
    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Size(max = 50, groups = QueryGroup.class)
    private String roleCode;
    /**
     * 数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）
     */
    @Schema(description = "数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）")
    private Boolean dataScope;
    /**
     * 菜单树父子选择项是否关联显示（1：是，0：否）
     */
    @Schema(description = "菜单树父子选择项是否关联显示（1：是，0：否）")
    private Boolean menuAssociationDisplayed;
    /**
     * 部门树父子选择项是否关联显示（1：是，0：否）
     */
    @Schema(description = "部门树父子选择项是否关联显示（1：是，0：否）")
    private Boolean deptAssociationDisplayed;
    /**
     * 状态（0：正式数据，1：删除）
     */
    @Schema(description = "状态（0：正式数据，1：删除）")
    private Boolean deleteFlag;
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
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private String createUserId;

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
    @Schema(description = "备注")
    @Size(max = 500, groups = QueryGroup.class)
    private String remark;
}

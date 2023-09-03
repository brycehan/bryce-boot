package com.brycehan.boot.system.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统角色表Vo
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Data
@TableName("brc_sys_role")
@Schema(description = "系统角色表Vo")
public class SysRoleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String code;

    /**
     * 数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）
     */
    @Schema(description = "数据范围（1：全部数据权限，2：自定数据权限，3：本部门数据权限，4：本部门及以下数据权限）")
    private Integer dataScope;

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
    private Boolean deleted;

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

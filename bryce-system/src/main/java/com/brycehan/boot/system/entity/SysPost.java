package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.boot.common.base.entity.BaseEntity;
import com.brycehan.boot.common.validator.AddGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统岗位
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Getter
@Setter
@TableName("brc_sys_post")
@Schema(description = "SysPost实体")
public class SysPost extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @Size(max = 64, groups = {AddGroup.class, UpdateGroup.class})
    private String postCode;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String postName;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private Long createdUserId;

    /**
     * 创建人账号
     */
    @Schema(description = "创建人账号")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;


}

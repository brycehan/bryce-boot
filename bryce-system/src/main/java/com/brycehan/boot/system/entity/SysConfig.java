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

import java.time.LocalDateTime;

/**
 * 系统配置
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Getter
@Setter
@TableName("brc_sys_config")
@Schema(description = "SysConfig实体")
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String configName;

    /**
     * 配置键
     */
    @Schema(description = "配置键")
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String configKey;

    /**
     * 配置值
     */
    @Schema(description = "配置值")
    @Size(max = 1000, groups = {AddGroup.class, UpdateGroup.class})
    private String configValue;

    /**
     * 是否系统内置（1：是，0：否）
     */
    @Schema(description = "是否系统内置（1：是，0：否）")
    private Boolean configType;

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

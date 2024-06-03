package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统岗位Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@Schema(description = "系统岗位Dto")
public class SysPostDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @Size(max = 30, groups = {SaveGroup.class, UpdateGroup.class})
    private String code;

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
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

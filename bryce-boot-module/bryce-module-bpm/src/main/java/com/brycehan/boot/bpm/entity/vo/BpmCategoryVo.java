package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.brycehan.boot.common.enums.StatusType;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 流程分类Vo
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@Schema(description = "流程分类Vo")
public class BpmCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String name;

    /**
     * 分类标志
     */
    @Schema(description = "分类标志")
    private String code;

    /**
     * 分类描述
     */
    @Schema(description = "分类描述")
    private String description;

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

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

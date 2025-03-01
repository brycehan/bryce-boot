package com.brycehan.boot.bpm.entity.vo;

import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表单定义Vo
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@Schema(description = "表单定义Vo")
public class BpmFormVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 表单名
     */
    @Schema(description = "表单名")
    private String name;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 表单的配置
     */
    @Schema(description = "表单的配置")
    private String conf;

    /**
     * 表单项的数组
     */
    @Schema(description = "表单项的数组")
    private String fields;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

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

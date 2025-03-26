package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.brycehan.boot.common.enums.StatusType;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 流程表达式Vo
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@Schema(description = "流程表达式Vo")
public class BpmProcessExpressionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 流程实例的名称
     */
    @Schema(description = "流程实例的名称")
    private String name;

    /**
     * 流程实例的状态
     */
    @Schema(description = "流程实例的状态")
    private StatusType status;

    /**
     * 表达式
     */
    @Schema(description = "表达式")
    private String expression;

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

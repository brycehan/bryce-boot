package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import com.brycehan.boot.common.enums.StatusType;
import java.time.LocalDateTime;

/**
 * 流程表达式 PageDto
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@Schema(description = "流程表达式PageDto")
@EqualsAndHashCode(callSuper = true)
public class BpmProcessExpressionPageDto extends BasePageDto {

    /**
     * 流程实例的名称
     */
    @Schema(description = "流程实例的名称")
    @Length(max = 64)
    private String name;

    /**
     * 流程实例的状态
     */
    @Schema(description = "流程实例的状态")
    private StatusType status;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createdTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createdTimeEnd;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

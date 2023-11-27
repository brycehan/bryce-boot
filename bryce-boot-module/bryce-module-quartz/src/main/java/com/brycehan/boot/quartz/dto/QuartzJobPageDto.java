package com.brycehan.boot.quartz.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * quartz 定时任务调度PageDto
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Data
@Schema(description = "quartz 定时任务调度PageDto")
@EqualsAndHashCode(callSuper = true)
public class QuartzJobPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Size(max = 50)
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    @Size(max = 50)
    private String jobGroup;

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

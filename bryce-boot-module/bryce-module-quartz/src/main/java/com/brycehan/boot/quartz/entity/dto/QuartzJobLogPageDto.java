package com.brycehan.boot.quartz.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * quartz定时任务调度日志PageDto
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "quartz定时任务调度日志PageDto")
public class QuartzJobLogPageDto extends BasePageDto {

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

}

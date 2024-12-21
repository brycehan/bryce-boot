package com.brycehan.boot.quartz.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.quartz.common.JobGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 50)
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private JobGroup jobGroup;

}

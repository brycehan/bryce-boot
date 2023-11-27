package com.brycehan.boot.quartz.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz 定时任务调度日志Vo
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Data
@Schema(description = "quartz 定时任务调度日志Vo")
public class QuartzJobLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long jobId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private String jobGroup;

    /**
     * Spring Bean 名称
     */
    @Schema(description = "Spring Bean 名称")
    private String beanName;

    /**
     * 执行方法
     */
    @Schema(description = "执行方法")
    private String method;

    /**
     * 参数
     */
    @Schema(description = "参数")
    private String params;

    /**
     * 执行状态（0：失败，1：成功）
     */
    @Schema(description = "执行状态（0：失败，1：成功）")
    private Boolean executeStatus;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    private Integer duration;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String errorInfo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

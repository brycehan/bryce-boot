package com.brycehan.boot.quartz.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz 定时任务调度Vo
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Data
@Schema(description = "quartz 定时任务调度Vo")
public class QuartzJobVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

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
     * cron 表达式
     */
    @Schema(description = "cron 表达式")
    private String cronExpression;

    /**
     * 是否并发执行（N：否，Y：是）
     */
    @Schema(description = "是否并发执行（N：否，Y：是）")
    private String concurrent;

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
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

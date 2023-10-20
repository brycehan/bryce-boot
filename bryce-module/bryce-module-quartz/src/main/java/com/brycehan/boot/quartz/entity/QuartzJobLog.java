package com.brycehan.boot.quartz.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz 定时任务调度日志entity
 *
 * @author Bryce Han
 * @since 2023/10/19
 */
@Data
@TableName("brc_quartz_job_log")
public class QuartzJobLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 任务ID
     */
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * Spring Bean 名称
     */
    private String beanName;

    /**
     * 执行方法
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 执行状态（0：失败，1：成功）
     */
    private Boolean executeStatus;

    /**
     * 执行时长（毫秒）
     */
    private Integer duration;

    /**
     * 异常信息
     */
    private String errorInfo;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

}

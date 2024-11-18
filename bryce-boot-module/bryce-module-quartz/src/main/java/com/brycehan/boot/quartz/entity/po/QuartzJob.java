package com.brycehan.boot.quartz.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.enums.YesNoType;
import com.brycehan.boot.quartz.common.QuartzStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * quartz定时任务调度entity
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_quartz_job")
public class QuartzJob extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * cron 表达式
     */
    private String cronExpression;

    /**
     * 是否并发执行（N：否，Y：是）
     */
    private YesNoType concurrent;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private QuartzStatus status;

    /**
     * 备注
     */
    private String remark;

}

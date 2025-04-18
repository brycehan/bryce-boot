package com.brycehan.boot.quartz.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.OperateStatus;
import com.brycehan.boot.quartz.common.JobGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * quartz定时任务调度日志Dto
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "quartz定时任务调度日志Dto")
public class QuartzJobLogDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
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
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private JobGroup jobGroup;

    /**
     * Spring Bean 名称
     */
    @Schema(description = "Spring Bean 名称")
    @Length(max = 200, groups = {SaveGroup.class, UpdateGroup.class})
    private String beanName;

    /**
     * 执行方法
     */
    @Schema(description = "执行方法")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String method;

    /**
     * 参数
     */
    @Schema(description = "参数")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String params;

    /**
     * 执行状态（0：失败，1：成功）
     */
    @Schema(description = "执行状态（0：失败，1：成功）")
    private OperateStatus executeStatus;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    private Integer duration;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    @Length(max = 3000, groups = {SaveGroup.class, UpdateGroup.class})
    private String errorInfo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

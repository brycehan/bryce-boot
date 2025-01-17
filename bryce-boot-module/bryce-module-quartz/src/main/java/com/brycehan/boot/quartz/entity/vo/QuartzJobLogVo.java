package com.brycehan.boot.quartz.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.brycehan.boot.common.enums.EnumDescConverter;
import com.brycehan.boot.common.enums.OperateStatus;
import com.brycehan.boot.quartz.common.JobGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz定时任务调度日志Vo
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Data
@Schema(description = "quartz定时任务调度日志Vo")
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class QuartzJobLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @NumberFormat(value = "#")
    @ColumnWidth(20)
    @ExcelProperty(value = "日志编号")
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
    @ColumnWidth(20)
    @ExcelProperty(value = "任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    @ColumnWidth(20)
    @ExcelProperty(value = "任务组名", converter = EnumDescConverter.class)
    private JobGroup jobGroup;

    /**
     * Spring Bean 名称
     */
    @Schema(description = "Spring Bean 名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "Spring Bean 名称")
    private String beanName;

    /**
     * 执行方法
     */
    @Schema(description = "执行方法")
    @ColumnWidth(20)
    @ExcelProperty(value = "执行方法")
    private String method;

    /**
     * 参数
     */
    @Schema(description = "参数")
    @ColumnWidth(14)
    @ExcelProperty(value = "参数")
    private String params;

    /**
     * 执行状态（0：失败，1：成功）
     */
    @Schema(description = "执行状态（0：失败，1：成功）")
    @ColumnWidth(20)
    @ExcelProperty(value = "执行状态", converter = EnumDescConverter.class)
    private OperateStatus executeStatus;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    @ColumnWidth(20)
    @ExcelProperty(value = "执行时长（毫秒）")
    private Integer duration;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    @ColumnWidth(20)
    @ExcelProperty(value = "异常信息")
    private String errorInfo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ColumnWidth(20)
    @ExcelProperty(value = "执行时间")
    private LocalDateTime createdTime;

}

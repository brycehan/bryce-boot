package com.brycehan.boot.quartz.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.brycehan.boot.common.enums.EnumDescConverter;
import com.brycehan.boot.common.enums.YesNoType;
import com.brycehan.boot.quartz.common.JobGroup;
import com.brycehan.boot.quartz.common.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz定时任务调度Vo
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Data
@Schema(description = "quartz定时任务调度Vo")
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
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
    @ColumnWidth(20)
    @ExcelProperty(value = "任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    @ColumnWidth(20)
    @ExcelProperty(value = "任务组名", converter = EnumDescConverter.class  )
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
     * cron 表达式
     */
    @Schema(description = "cron 表达式")
    @ColumnWidth(20)
    @ExcelProperty(value = "执行表达式")
    private String cronExpression;

    /**
     * 是否并发执行
     */
    @Schema(description = "是否并发执行")
    @ColumnWidth(20)
    @ExcelProperty(value = "并发执行", converter = EnumDescConverter.class)
    private YesNoType concurrent;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @ColumnWidth(14)
    @ExcelProperty(value = "状态", converter = EnumDescConverter.class)
    private JobStatus status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

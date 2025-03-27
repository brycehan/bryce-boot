package com.brycehan.boot.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.brycehan.boot.common.enums.EnumDescConverter;
import com.brycehan.boot.common.enums.OperateStatus;
import com.brycehan.boot.common.enums.SourceClientType;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志Vo
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Data
@Schema(description = "系统操作日志Vo")
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class SysOperateLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @NumberFormat(value = "#")
    @ColumnWidth(20)
    @ExcelProperty(value = "日志编号", index = 0)
    private Long id;

    /**
     * 操作名称
     */
    @Schema(description = "操作名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "操作名称", index = 2)
    private String name;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "操作模块", index = 1)
    private String moduleName;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    @ColumnWidth(30)
    @ExcelProperty(value = "请求URI", index = 4)
    private String requestUri;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    @ColumnWidth(15)
    @ExcelProperty(value = "请求方式", index = 5)
    private String requestMethod;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    @ColumnWidth(30)
    @ExcelProperty(value = "请求参数", index = 6)
    private String requestParam;

    /**
     * 返回结果
     */
    @Schema(description = "返回结果")
    @ColumnWidth(40)
    @ExcelProperty(value = "返回结果", index = 12)
    private String jsonResult;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    @ColumnWidth(20)
    @ExcelProperty(value = "错误消息", index = 14)
    private String errorMessage;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    @ColumnWidth(15)
    @ExcelProperty(value = "操作类型", index = 3, converter = EnumDescConverter.class)
    private OperatedType operatedType;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    @ColumnWidth(20)
    @ExcelProperty(value = "操作时间")
    private LocalDateTime operatedTime;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    @NumberFormat("#毫秒")
    @ColumnWidth(15)
    @ExcelProperty(value = "执行时长")
    private Integer duration;

    /**
     * 操作状态（0：失败，1：成功）
     */
    @Schema(description = "操作状态（0：失败，1：成功）")
    @ColumnWidth(10)
    @ExcelProperty(value = "状态", index = 13, converter = EnumDescConverter.class)
    private OperateStatus status;

    /**
     * 操作IP
     */
    @Schema(description = "操作IP")
    @ColumnWidth(20)
    @ExcelProperty(value = "操作IP", index = 10)
    private String ip;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点")
    @ColumnWidth(20)
    @ExcelProperty(value = "操作地点", index = 11)
    private String location;

    /**
     * 来源客户端
     */
    @ColumnWidth(15)
    @ExcelProperty(value = "来源客户端", converter = EnumDescConverter.class, index = 9)
    private SourceClientType sourceClient;

    /**
     * User Agent
     */
    @Schema(description = "User Agent")
    private String userAgent;

    /**
     * 操作人ID
     */
    @Schema(description = "操作人ID")
    private Long userId;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    @ColumnWidth(15)
    @ExcelProperty(value = "操作账号", index = 7)
    private String username;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "部门名称", index = 8)
    private String orgName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

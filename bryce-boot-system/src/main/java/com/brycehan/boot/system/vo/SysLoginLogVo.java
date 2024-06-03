package com.brycehan.boot.system.vo;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统登录日志Vo
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "系统登录日志Vo")
public class SysLoginLogVo implements Serializable, TransPojo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 用户账号
     */
    @ColumnWidth(14)
    @ExcelProperty(value = "用户账号", index = 0)
    @Schema(description = "用户账号")
    private String username;

    /**
     * 操作信息
     */
    @Trans(type = TransType.DICTIONARY, key = "sys_login_status", ref = "infoLabel")
    @Schema(description = "操作信息")
    private Integer info;

    @ColumnWidth(14)
    @ExcelProperty(value = "操作信息", index = 1)
    private String infoLabel;

    /**
     * 登录IP
     */
    @ColumnWidth(14)
    @ExcelProperty(value = "登录IP", index = 3)
    @Schema(description = "登录IP")
    private String ip;

    /**
     * 登录地点
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "登录地点", index = 4)
    @Schema(description = "登录地点")
    private String location;

    /**
     * 浏览器类型
     */
    @ColumnWidth(12)
    @ExcelProperty(value = "浏览器", index = 5)
    @Schema(description = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @ColumnWidth(14)
    @ExcelProperty(value = "操作系统", index = 6)
    @Schema(description = "操作系统")
    private String os;

    /**
     * User Agent
     */
    @ColumnWidth(30)
    @ExcelProperty(value = "User Agent", index = 7)
    @Schema(description = "User Agent")
    private String userAgent;

    /**
     * 状态（0：失败，1：成功）
     */
    @Schema(description = "状态（0：失败，1：成功）")
    @Trans(type = TransType.DICTIONARY, key = "sys_operate_status", ref = "statusLabel")
    private Boolean status;

    /**
     * 状态（0：失败，1：成功）
     */
    @ColumnWidth(14)
    @ExcelProperty(value = "操作状态", index = 2)
    private String statusLabel;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    @ColumnWidth(20)
    @ExcelProperty(value = "访问时间", index = 8)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime accessTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ColumnWidth(20)
    @ExcelProperty(value = "创建时间", index = 9)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

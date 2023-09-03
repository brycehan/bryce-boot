package com.brycehan.boot.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志视图对象
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Data
@Schema(description = "SysOperateLogVo")
public class SysOperateLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 模块标题
     */
    @Schema(description = "模块标题")
    private String title;

    /**
     * 业务类型（0：其它，1：新增，2：修改，3：删除）
     */
    @Schema(description = "业务类型（0：其它，1：新增，2：修改，3：删除）")
    private Integer businessType;

    /**
     * 方法名称
     */
    @Schema(description = "方法名称")
    private String method;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @Schema(description = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    /**
     * 操作人员ID
     */
    @Schema(description = "操作人员ID")
    private Long operationUserId;

    /**
     * 操作人员账号
     */
    @Schema(description = "操作人员账号")
    private String operationUsername;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 请求URL
     */
    @Schema(description = "请求URL")
    private String operationUrl;

    /**
     * 主机地址
     */
    @Schema(description = "主机地址")
    private String operationIp;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点")
    private String operationLocation;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String operationParam;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operationTime;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    private Integer duration;

    /**
     * 操作状态（0：正常，1：异常）
     */
    @Schema(description = "操作状态（0：正常，1：异常）")
    private Boolean operationStatus;

    /**
     * 返回参数
     */
    @Schema(description = "返回参数")
    private String jsonResult;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String errorMessage;

}

package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BasePo;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统操作日志
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Getter
@Setter
@TableName("brc_sys_operation_log")
@Schema(description = "SysOperationLog实体")
public class SysOperationLog extends BasePo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 模块标题
     */
    @Schema(description = "模块标题")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
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
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    private String method;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    @Size(max = 10, groups = {AddGroup.class, UpdateGroup.class})
    private String requestMethod;

    /**
     * 操作员类别（0：后台用户，1：手机端用户，2：其它）
     */
    @Schema(description = "操作类别（0：后台用户，1：手机端用户，2：其它）")
    private Integer operatorType;

    /**
     * 操作人员ID
     */
    @Schema(description = "操作人员ID")
    @Size(max = 36, groups = {AddGroup.class, UpdateGroup.class})
    private String operationUserId;

    /**
     * 操作人员账号
     */
    @Schema(description = "操作人员账号")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String operationUsername;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String deptName;

    /**
     * 请求URL
     */
    @Schema(description = "请求URL")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String operationUrl;

    /**
     * 主机地址
     */
    @Schema(description = "主机地址")
    @Size(max = 128, groups = {AddGroup.class, UpdateGroup.class})
    private String operationIp;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String operationLocation;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    @Size(max = 2000, groups = {AddGroup.class, UpdateGroup.class})
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
     * 操作状态（0：异常，1：正常）
     */
    @Schema(description = "操作状态（0：异常，1：正常）")
    private Boolean operationStatus;

    /**
     * 返回参数
     */
    @Schema(description = "返回参数")
    @Size(max = 2000, groups = {AddGroup.class, UpdateGroup.class})
    private String jsonResult;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    @Size(max = 2000, groups = {AddGroup.class, UpdateGroup.class})
    private String errorMessage;

}

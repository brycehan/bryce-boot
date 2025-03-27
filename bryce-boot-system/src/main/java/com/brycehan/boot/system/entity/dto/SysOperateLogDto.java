package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.OperateStatus;
import com.brycehan.boot.common.enums.SourceClientType;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 系统操作日志Dto
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统操作日志Dto")
public class SysOperateLogDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 操作名称
     */
    @Schema(description = "操作名称")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 模块名
     */
    @Schema(description = "模块名")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String moduleName;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    @Length(max = 2048, groups = {SaveGroup.class, UpdateGroup.class})
    private String requestUri;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    @Length(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String requestMethod;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    @Length(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String requestParam;

    /**
     * 返回结果
     */
    @Schema(description = "返回结果")
    @Length(max = 2000, groups = {SaveGroup.class, UpdateGroup.class})
    private String jsonResult;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    @Length(max = 2000, groups = {SaveGroup.class, UpdateGroup.class})
    private String errorMessage;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    private OperatedType operatedType;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    private LocalDateTime operatedTime;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    private Integer duration;

    /**
     * 操作状态（0：失败，1：成功）
     */
    @Schema(description = "操作状态（0：失败，1：成功）")
    private OperateStatus status;

    /**
     * 操作IP
     */
    @Schema(description = "操作IP")
    @Length(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String ip;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String location;

    /**
     * 来源客户端
     */
    @Schema(description = "来源客户端")
    private SourceClientType sourceClient;

    /**
     * User Agent
     */
    @Schema(description = "User Agent")
    @Length(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
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
    private String deptName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

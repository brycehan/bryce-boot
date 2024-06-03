package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志Dto
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Data
@Schema(description = "系统操作日志Dto")
public class SysOperateLogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 模块名
     */
    @Schema(description = "模块名")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String moduleName;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    @Size(max = 2048, groups = {SaveGroup.class, UpdateGroup.class})
    private String requestUri;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String requestMethod;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String requestParam;

    /**
     * 返回消息
     */
    @Schema(description = "返回消息")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String resultMessage;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String operatedType;

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
    private Boolean status;

    /**
     * User Agent
     */
    @Schema(description = "User Agent")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String userAgent;

    /**
     * 操作IP
     */
    @Schema(description = "操作IP")
    @Size(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String ip;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String location;

    /**
     * 操作人ID
     */
    @Schema(description = "操作人ID")
    private Long userId;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

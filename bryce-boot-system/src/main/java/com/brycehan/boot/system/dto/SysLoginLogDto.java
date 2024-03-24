package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统登录日志Dto
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Data
@Schema(description = "系统登录日志Dto")
public class SysLoginLogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 操作信息
     */
    @Schema(description = "操作信息")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private Integer info;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    @Size(max = 128, groups = {SaveGroup.class, UpdateGroup.class})
    private String ip;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String location;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String os;

    /**
     * User Agent
     */
    @Schema(description = "User Agent")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String userAgent;

    /**
     * 状态（0：失败，1：成功）
     */
    @Schema(description = "状态（0：失败，1：成功）")
    private Boolean status;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    private LocalDateTime accessTime;

}

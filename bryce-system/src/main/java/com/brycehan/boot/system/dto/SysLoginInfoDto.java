package com.brycehan.boot.system.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统登录信息Dto
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Data
@TableName("brc_sys_login_log")
@Schema(description = "系统登录信息Dto")
public class SysLoginInfoDto implements Serializable {

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
    @Schema(description = "用户账号")
    private String username;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 登录状态（0：失败，1：成功）
     */
    @Schema(description = "登录状态（0：失败，1：成功）")
    private Integer status;

    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    private String message;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    private LocalDateTime loginTime;

}

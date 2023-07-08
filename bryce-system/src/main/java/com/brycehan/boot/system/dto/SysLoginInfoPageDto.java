package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.group.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 系统登录信息分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Getter
@Setter
@Schema(description = "SysLoginInfoPageDto对象")
public class SysLoginInfoPageDto extends BasePageDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 用户账号
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "用户账号")
    private String username;

    /**
     * 登录IP地址
     */
    @Size(max = 128, groups = QueryGroup.class)
    @Schema(description = "登录IP地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Size(max = 50, groups = QueryGroup.class)
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
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "提示消息")
    private String message;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginTime;

}

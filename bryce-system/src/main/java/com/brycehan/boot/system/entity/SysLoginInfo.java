package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BasePo;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 系统登录信息
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Getter
@Setter
@TableName("brc_sys_login_info")
@Schema(description = "SysLoginInfo实体")
public class SysLoginInfo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    @Size(max = 128, groups = {AddGroup.class, UpdateGroup.class})
    private String ipaddr;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String os;

    /**
     * 登录状态（0：失败，1：成功）
     */
    @Schema(description = "登录状态（0：失败，1：成功）")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    @Size(max = 255, groups = {AddGroup.class, UpdateGroup.class})
    private String message;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginTime;


}

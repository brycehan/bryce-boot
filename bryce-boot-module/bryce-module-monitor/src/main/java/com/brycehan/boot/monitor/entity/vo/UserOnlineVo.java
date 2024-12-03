package com.brycehan.boot.monitor.entity.vo;

import com.brycehan.boot.common.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 在线用户 vo
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Data
@Schema(description = "在级用户 vo")
public class UserOnlineVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String nickname;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private GenderType gender;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String orgName;

    /**
     * 用户登录存储Key
     */
    private String userKey;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String loginIp;

    /**
     * 登录位置
     */
    @Schema(description = "登录位置")
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

}

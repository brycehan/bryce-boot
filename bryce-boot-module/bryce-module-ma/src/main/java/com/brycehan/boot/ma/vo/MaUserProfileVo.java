package com.brycehan.boot.ma.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 微信小程序用户 Vo
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "微信小程序用户个人信息Vo")
public class MaUserProfileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 账户名
     */
    @Schema(description = "账户名")
    private String account;

    /**
     * 性别（0女，1男）
     */
    @Schema(description = "性别（0女，1男）")
    private String gender;

    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private LocalDate birthday;

    /**
     * 省市区
     */
    @Schema(description = "省市区")
    private String fullLocation;

    /**
     * 职业
     */
    @Schema(description = "职业")
    private String profession;
}

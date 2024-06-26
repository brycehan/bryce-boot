package com.brycehan.boot.common.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户个人信息Vo
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "用户个人信息Vo")
public class ProfileVo implements Serializable {

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
     * 账号
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 性别（0女，1男）
     */
    @Schema(description = "性别（0女，1男）")
    private String gender;

    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private LocalDate birthday;

    /**
     * 所在省份
     */
    @Schema(description = "所在省份")
    private String province;

    /**
     * 所在城市
     */
    @Schema(description = "所在城市")
    private String city;

    /**
     * 区/县编码
     */
    @Schema(description = "区/县编码")
    private String county;

    /**
     * 用户省市区/县名称
     */
    @Schema(description = "用户省市区/县名称")
    private String fullLocation;

    /**
     * 职业
     */
    @Schema(description = "职业")
    private String profession;

}

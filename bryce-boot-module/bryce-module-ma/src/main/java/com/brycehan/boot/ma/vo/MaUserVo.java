package com.brycehan.boot.ma.vo;

import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 微信小程序用户 Vo
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "微信小程序用户Vo")
public class MaUserVo implements Serializable {

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
     * 性别（0女，1男）
     */
    @Schema(description = "性别（0女，1男）")
    private String gender;

    /**
     * 所在国家
     */
    @Schema(description = "所在国家")
    private String country;

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
     * 用户语言
     */
    @Schema(description = "用户语言")
    private String language;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * openid
     */
    @Schema(description = "openid")
    private String openId;

    /**
     * union_id
     */
    @Schema(description = "union_id")
    private String unionId;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;

    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime birthday;

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

    /**
     * 标签ID列表
     */
    @Schema(description = "标签ID列表")
    private Object tagIds;

    /**
     * 用户组
     */
    @Schema(description = "用户组")
    private String groupId;

    /**
     * 二维码扫码场景
     */
    @Schema(description = "二维码扫码场景")
    private String qrSceneStr;

    /**
     * 地理位置纬度
     */
    @Schema(description = "地理位置纬度")
    private Double geoLatitude;

    /**
     * 地理位置经度
     */
    @Schema(description = "地理位置经度")
    private Double geoLongitude;

    /**
     * 地理位置精度
     */
    @Schema(description = "地理位置精度")
    private Double geoPrecision;

    /**
     * 会话密钥
     */
    @Schema(description = "会话密钥")
    private String sessionKey;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

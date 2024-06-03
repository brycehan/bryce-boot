package com.brycehan.boot.mp.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信公众号粉丝 Vo
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号粉丝Vo")
public class MpUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    @Schema(description = "openid")
    private String openId;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String headImgUrl;

    /**
     * 性别（0未知，1男，2女）
     */
    @Schema(description = "性别（0未知，1男，2女）")
    private Integer sex;

    /**
     * 省份
     */
    @Schema(description = "省份")
    private String province;

    /**
     * 城市
     */
    @Schema(description = "城市")
    private String city;

    /**
     * 标签ID列表
     */
    @Schema(description = "标签ID列表")
    private Object tagIds;

    /**
     * 是否关注
     */
    @Schema(description = "是否关注")
    private Boolean subscribe;

    /**
     * 关注时间
     */
    @Schema(description = "关注时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime subscribeTime;

    /**
     * 关注场景
     */
    @Schema(description = "关注场景")
    private String subscribeScene;

    /**
     * 扫码场景值
     */
    @Schema(description = "扫码场景值")
    private String qrSceneStr;

    /**
     * unionid
     */
    @Schema(description = "unionid")
    private String unionId;

    /**
     * 语言
     */
    @Schema(description = "语言")
    private String language;

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

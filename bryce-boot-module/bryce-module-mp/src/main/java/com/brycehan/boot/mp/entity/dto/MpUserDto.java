package com.brycehan.boot.mp.entity.dto;

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
 * 微信公众号粉丝Dto
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号粉丝Dto")
public class MpUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    @Schema(description = "openid")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String openId;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String province;

    /**
     * 城市
     */
    @Schema(description = "城市")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
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
    private LocalDateTime subscribeTime;

    /**
     * 关注场景
     */
    @Schema(description = "关注场景")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String subscribeScene;

    /**
     * 扫码场景值
     */
    @Schema(description = "扫码场景值")
    @Size(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String qrSceneStr;

    /**
     * unionid
     */
    @Schema(description = "unionid")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String unionId;

    /**
     * 语言
     */
    @Schema(description = "语言")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String language;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedTime;

}

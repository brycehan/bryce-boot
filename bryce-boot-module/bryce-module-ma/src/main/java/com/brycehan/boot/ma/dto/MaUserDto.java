package com.brycehan.boot.ma.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 微信小程序用户Dto
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "微信小程序用户 Dto")
public class MaUserDto implements Serializable {

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
    @Size(max = 50, groups = UpdateGroup.class)
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    @Size(max = 255, groups = UpdateGroup.class)
    private String avatar;

    /**
     * 性别（0未知，1男，2女）
     */
    @Schema(description = "性别（0未知，1男，2女）")
    private String gender;

    /**
     * 所在国家
     */
    @Schema(description = "所在国家")
    @Size(max = 64, groups = UpdateGroup.class)
    private String country;

    /**
     * 所在省份
     */
    @Schema(description = "所在省份")
    @Size(max = 64, groups = UpdateGroup.class)
    private String province;

    /**
     * 所在城市
     */
    @Schema(description = "所在城市")
    @Size(max = 64, groups = UpdateGroup.class)
    private String city;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private LocalDate birthday;

    /**
     * 省市区
     */
    @Schema(description = "省市区")
    @Size(max = 100, groups = UpdateGroup.class)
    private String fullLocation;

    /**
     * 职业
     */
    @Schema(description = "职业")
    @Size(max = 50, groups = UpdateGroup.class)
    private String profession;

    /**
     * 标签ID列表
     */
    @Schema(description = "标签ID列表")
    private Object tagIds;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 255, groups = UpdateGroup.class)
    private String remark;

}

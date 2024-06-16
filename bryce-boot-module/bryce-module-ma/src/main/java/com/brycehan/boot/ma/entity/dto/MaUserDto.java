package com.brycehan.boot.ma.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 微信小程序用户Dto
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信小程序用户Dto")
public class MaUserDto extends BaseDto {

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
     * 省份编码
     */
    @Schema(description = "省份编码")
    @Size(max = 64, groups = UpdateGroup.class)
    private String province;

    /**
     * 城市编码
     */
    @Schema(description = "城市编码")
    @Size(max = 64, groups = UpdateGroup.class)
    private String city;

    /**
     * 区/县编码
     */
    private String county;

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

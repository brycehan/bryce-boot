package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 微信菜单项Dto
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信菜单项Dto")
public class MpMenuItemDto extends BaseDto {

    /**
     * 菜单类型
     */
    @Schema(description = "菜单类型")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 菜单key
     */
    @Schema(description = "菜单key")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String key;

    /**
     * 菜单URL
     */
    @Schema(description = "菜单URL")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 小程序appId
     */
    @Schema(description = "小程序appId")
    @Size(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String appId;

    /**
     * 小程序的页面路径
     */
    @Schema(description = "小程序的页面路径")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String pagepath;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private List<MpMenuItemDto> children;

}

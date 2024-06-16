package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

/**
 * 微信公众号消息回复规则Dto
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信公众号消息回复规则Dto")
public class MpMessageReplyRuleDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 匹配的关键词、事件等
     */
    @Schema(description = "匹配的关键词、事件等")
    @Size(max = 200, groups = {SaveGroup.class, UpdateGroup.class})
    private String matchValue;

    /**
     * 是否精确匹配（1：精确，1：不精确）
     */
    @Schema(description = "是否精确匹配（1：精确，1：不精确）")
    private Boolean exactMatch;

    /**
     * 回复消息类型
     */
    @Schema(description = "回复消息类型")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String replyType;

    /**
     * 回复消息内容
     */
    @Schema(description = "回复消息内容")
    @Size(max = 1000, groups = {SaveGroup.class, UpdateGroup.class})
    private String replyContent;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 生效起始时间
     */
    @Schema(description = "生效起始时间")
    private LocalTime effectTimeStart;

    /**
     * 生效结束时间
     */
    @Schema(description = "生效结束时间")
    private LocalTime effectTimeEnd;

    /**
     * 规则优先级
     */
    @Schema(description = "规则优先级")
    private Integer priority;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

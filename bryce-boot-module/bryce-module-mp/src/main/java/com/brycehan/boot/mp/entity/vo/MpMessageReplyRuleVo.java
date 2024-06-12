package com.brycehan.boot.mp.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 微信公众号消息回复规则Vo
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号消息回复规则Vo")
public class MpMessageReplyRuleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 匹配的关键词、事件等
     */
    @Schema(description = "匹配的关键词、事件等")
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
    private String replyType;

    /**
     * 回复消息内容
     */
    @Schema(description = "回复消息内容")
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
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

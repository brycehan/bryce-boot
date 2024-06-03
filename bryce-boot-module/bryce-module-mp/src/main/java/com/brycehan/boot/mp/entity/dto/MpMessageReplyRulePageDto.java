package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信公众号消息回复规则 PageDto
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号消息回复规则 PageDto")
@EqualsAndHashCode(callSuper = true)
public class MpMessageReplyRulePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @Size(max = 50)
    private String name;

    /**
     * 匹配的关键词、事件等
     */
    @Schema(description = "匹配的关键词、事件等")
    @Size(max = 200)
    private String matchValue;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

}

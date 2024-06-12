package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信公众号消息Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信公众号消息Dto")
public class MpMessageReplyDto extends BaseDto {

    /**
     * openid
     */
    @Schema(description = "openid")
    @NotEmpty(message = "用户信息不能为空")
    @Size(max = 50)
    private String openId;

    /**
     * 消息类型
     */
    @Schema(description = "回复类型")
    @NotEmpty(message = "回复类型不能为空")
    @Size(max = 25)
    private String replyType;

    /**
     * 消息内容
     */
    @Schema(description = "回复内容")
    @NotEmpty(message = "回复内容不能为空")
    private String content;

}

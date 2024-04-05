package com.brycehan.boot.mp.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 微信公众号模版消息发送记录Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号模版消息发送记录Dto")
public class MpTemplateMessageLogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 用户openid
     */
    @Schema(description = "用户openid")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String toUser;

    /**
     * templateid
     */
    @Schema(description = "templateid")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String templateId;

    /**
     * 消息数据
     */
    @Schema(description = "消息数据")
    private String data;

    /**
     * 消息链接
     */
    @Schema(description = "消息链接")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 小程序信息
     */
    @Schema(description = "小程序信息")
    private String miniProgram;

    /**
     * 发送时间
     */
    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    /**
     * 发送结果
     */
    @Schema(description = "发送结果")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String sendResult;

}

package com.brycehan.boot.mp.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 微信公众号消息Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号消息Dto")
public class MpMessageDto implements Serializable {

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
     * openid
     */
    @Schema(description = "openid")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String openId;

    /**
     * 消息方向（1：in，0：out）
     */
    @Schema(description = "消息方向（1：in，0：out）")
    private Boolean inOut;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    @Size(max = 25, groups = {SaveGroup.class, UpdateGroup.class})
    private String messageType;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String content;

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

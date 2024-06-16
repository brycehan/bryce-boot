package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信公众号消息PageDto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号消息PageDto")
@EqualsAndHashCode(callSuper = true)
public class MpMessagePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    @Size(max = 25)
    private String messageType;

}

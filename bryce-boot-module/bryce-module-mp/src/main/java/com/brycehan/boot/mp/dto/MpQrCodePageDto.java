package com.brycehan.boot.mp.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import java.io.Serial;

/**
 * 微信公众号带参二维码 PageDto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号带参二维码 PageDto")
@EqualsAndHashCode(callSuper = true)
public class MpQrCodePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 场景值ID
     */
    @Schema(description = "场景值ID")
    @Size(max = 64)
    private String sceneStr;

}
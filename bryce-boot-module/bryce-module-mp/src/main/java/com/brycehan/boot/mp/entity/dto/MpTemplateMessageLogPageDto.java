package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信公众号模版消息发送记录PageDto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号模版消息发送记录PageDto")
@EqualsAndHashCode(callSuper = true)
public class MpTemplateMessageLogPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

}

package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信公众号消息模板 PageDto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号消息模板 PageDto")
@EqualsAndHashCode(callSuper = true)
public class MpMessageTemplatePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模版名称
     */
    @Schema(description = "模版名称")
    @Size(max = 50)
    private String name;

    /**
     * 是否有效
     */
    @Schema(description = "是否有效")
    private Boolean status;

}

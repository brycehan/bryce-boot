package com.brycehan.boot.mp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信公众号粉丝标签Dto
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号粉丝标签Dto")
public class MpUserTagDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tagId
     */
    @Schema(description = "tagId")
    private Long tagId;

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    @NotEmpty(message = "标签名称不能为空")
    @Size(max = 30, message = "标签名称长度必须为1-30字符")
    private String name;

}

package com.brycehan.boot.mp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信公众号粉丝标签批量Dto
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@Schema(description = "微信公众号粉丝标签批量Dto")
public class MpUserTagBatchDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tagId
     */
    @Schema(description = "tagId")
    @NotNull(message = "标签ID不能为空")
    private Long tagId;

    /**
     * openid列表
     */
    @Schema(description = "openid列表")
    @NotEmpty(message = "标签名称不能为空")
    @Length(max = 50, message = "每次处理数量为1-50个")
    private String[] openidList;

}

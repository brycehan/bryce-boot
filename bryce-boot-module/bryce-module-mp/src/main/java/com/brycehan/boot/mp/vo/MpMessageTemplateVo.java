package com.brycehan.boot.mp.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.io.Serial;

/**
 * 微信公众号消息模板 Vo
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号消息模板Vo")
public class MpMessageTemplateVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 公众号模板ID
     */
    @Schema(description = "公众号模板ID")
    private String templateId;

    /**
     * 模版名称
     */
    @Schema(description = "模版名称")
    private String name;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    private String content;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private Object data;

    /**
     * 链接
     */
    @Schema(description = "链接")
    private String url;

    /**
     * 小程序信息
     */
    @Schema(description = "小程序信息")
    private Object miniProgram;

    /**
     * 是否有效
     */
    @Schema(description = "是否有效")
    private Boolean status;

}

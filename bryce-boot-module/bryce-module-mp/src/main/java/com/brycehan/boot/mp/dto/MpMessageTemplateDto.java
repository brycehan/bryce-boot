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
 * 微信公众号消息模板Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@Schema(description = "微信公众号消息模板Dto")
public class MpMessageTemplateDto implements Serializable {

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
     * 公众号模板ID
     */
    @Schema(description = "公众号模板ID")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String templateId;

    /**
     * 模版名称
     */
    @Schema(description = "模版名称")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String title;

    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
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

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedTime;

}

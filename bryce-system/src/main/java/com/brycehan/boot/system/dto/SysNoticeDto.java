package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统通知公告Dto
 *
 * @author Bryce Han
 * @since 2023/10/13
 */
@Data
@Schema(description = "系统通知公告Dto")
public class SysNoticeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "ID")
    private Long id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 公告类型（0：通知，1：公告）
     */
    @Schema(description = "公告类型（0：通知，1：公告）")
    private Integer type;

    /**
     * 状态（0：关闭，1：正常）
     */
    @Schema(description = "状态（0：关闭，1：正常）")
    private Boolean status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}

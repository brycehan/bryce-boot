package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.NoticeType;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统通知公告Dto
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统通知公告Dto")
public class SysNoticeDto extends BaseDto {

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
    private NoticeType type;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}

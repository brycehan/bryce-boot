package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.common.enums.NoticeType;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 系统通知公告PageDto
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统通知公告PageDto")
public class SysNoticePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Length(max = 100)
    private String title;

    /**
     * 创建者账号
     */
    @Schema(description = "创建者账号")
    @Length(max = 50)
    private String createdUsername;

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

}

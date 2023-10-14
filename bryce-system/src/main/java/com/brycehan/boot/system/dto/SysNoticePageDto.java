package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serial;

/**
 * 系统通知公告PageDto
 *
 * @author Bryce Han
 * @since 2023/10/13
 */
@Data
@Schema(description = "系统通知公告PageDto")
@EqualsAndHashCode(callSuper = false)
public class SysNoticePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}

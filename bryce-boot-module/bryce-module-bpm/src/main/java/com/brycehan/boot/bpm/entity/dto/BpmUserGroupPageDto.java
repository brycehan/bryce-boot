package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import com.brycehan.boot.common.enums.StatusType;

import java.time.LocalDateTime;

/**
 * 用户组PageDto
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Data
@Schema(description = "用户组PageDto")
@EqualsAndHashCode(callSuper = true)
public class BpmUserGroupPageDto extends BasePageDto {

    /**
     * 组名
     */
    @Schema(description = "组名")
    @Length(max = 30)
    private String name;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createdTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createdTimeEnd;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;
}

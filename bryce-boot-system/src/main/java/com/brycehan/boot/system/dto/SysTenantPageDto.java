package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统租户PageDto
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Data
@Schema(description = "系统租户PageDto")
@EqualsAndHashCode(callSuper = false)
public class SysTenantPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    @Size(max = 100)
    private String name;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

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

}

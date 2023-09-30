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
 * 系统岗位PageDto
 *
 * @author Bryce Han
 * @since 2023/09/28
 */
@Data
@Schema(description = "系统岗位PageDto")
@EqualsAndHashCode(callSuper = false)
public class SysPostPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @Size(max = 50)
    private String name;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @Size(max = 30)
    private String code;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}

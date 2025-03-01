package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import com.brycehan.boot.common.enums.StatusType;

/**
 * 表单定义PageDto
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@Schema(description = "表单定义PageDto")
@EqualsAndHashCode(callSuper = true)
public class BpmFormPageDto extends BasePageDto {

    /**
     * 表单名
     */
    @Schema(description = "表单名")
    @Length(max = 64)
    private String name;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

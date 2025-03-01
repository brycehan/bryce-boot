package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 流程定义信息PageDto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@Schema(description = "流程定义信息PageDto")
@EqualsAndHashCode(callSuper = true)
public class BpmProcessDefinitionInfoPageDto extends BasePageDto {

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

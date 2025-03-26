package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import com.brycehan.boot.common.enums.StatusType;

/**
 * 流程监听器 PageDto
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@Schema(description = "流程监听器PageDto")
@EqualsAndHashCode(callSuper = true)
public class BpmProcessListenerPageDto extends BasePageDto {

    /**
     * 监听器名称
     */
    @Schema(description = "监听器名称")
    @Length(max = 255)
    private String name;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private StatusType status;

    /**
     * 监听类型
     */
    @Schema(description = "监听类型")
    @Length(max = 255)
    private String type;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}

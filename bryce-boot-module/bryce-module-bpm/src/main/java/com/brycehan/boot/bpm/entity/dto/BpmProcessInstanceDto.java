package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 流程实例Dto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程实例Dto")
public class BpmProcessInstanceDto extends BaseDto {

    /**
     * 流程定义的编号
     */
    @Schema(description = "流程定义的编号")
    private String processDefinitionId;

    /**
     * 流程定义的标识
     */
    private String processDefinitionKey;

    /**
     * 变量实例（动态表单）
     */
    @Schema(description = "变量实例（动态表单）")
    private Map<String, Object> variables;

    /**
     * 业务的唯一标识
     * <br>
     * 例如说，请假申请的编号。通过它，可以查询到对应的实例
     */
    @Schema(description = "业务的唯一标识")
    private String businessKey;

    /**
     * 发起人自选审批人 Map
     */
    @Schema(description = "发起人自选审批人 Map", example = "{taskKey2: [1, 2]}")
    private Map<String, List<Long>> startUserSelectAssignees;

}

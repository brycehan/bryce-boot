package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * BPM 发送流程实例被不通过 Dto
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Schema(description = "BPM 发送流程实例被不通过 Dto")
@Data
@Accessors(chain = true)
public class BpmMessageSendWhenProcessInstanceRejectDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程实例的编号
     */
    @NotEmpty
    @Schema(description = "流程实例的编号")
    private String processInstanceId;

    /**
     * 流程实例的名称
     */
    @NotEmpty
    @Schema(description = "流程实例的名称")
    private String processInstanceName;

    /**
     * 发起人的用户编号
     */
    @NotNull
    @Schema(description = "发起人的用户编号")
    private Long startUserId;

    /**
     * 不通过理由
     */
    @NotEmpty
    @Schema(description = "不通过理由")
    private String reason;

}

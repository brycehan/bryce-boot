package com.brycehan.boot.bpm.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * BPM 发送流程实例被通过 Dto
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
public class BpmMessageSendWhenProcessInstanceApproveDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程实例的编号
     */
    @NotEmpty
    private String processInstanceId;
    /**
     * 流程实例的名字
     */
    @NotEmpty
    private String processInstanceName;

    @NotNull
    private Long startUserId;

}

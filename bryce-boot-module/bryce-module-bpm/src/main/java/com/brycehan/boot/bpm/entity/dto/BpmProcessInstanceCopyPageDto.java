package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 流程实例抄送的分页 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程实例抄送的分页 Dto")
public class BpmProcessInstanceCopyPageDto extends BasePageDto {

    @Schema(description = "流程名称")
    private String processInstanceName;

    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}

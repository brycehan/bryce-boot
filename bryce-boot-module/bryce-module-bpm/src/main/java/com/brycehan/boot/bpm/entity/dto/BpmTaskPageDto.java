package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 流程任务的的分页 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "流程任务的的分页 Dto") // 待办、已办，都使用该分页
public class BpmTaskPageDto extends BasePageDto {

    @Schema(description = "流程任务名")
    private String name;

    @Schema(description = "流程分类", example = "1")
    private String category;

    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}

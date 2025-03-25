package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 流程实例分页 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "流程实例分页 Dto")
public class BpmProcessInstancePageDto extends BasePageDto {

    @Schema(description = "流程实例编号")
    private String id;

    @Schema(description = "流程名称")
    private String name;

    @Schema(description = "流程定义的标识", example = "2048")
    private String processDefinitionKey; // 精准匹配

    @Schema(description = "流程实例的状态", example = "1")
    private Integer status;

    @Schema(description = "流程分类", example = "1")
    private String category;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createTimeEnd;

    @Schema(description = "结束时间开始")
    private LocalDateTime endTimeStart;

    @Schema(description = "结束时间结束")
    private LocalDateTime endTimeEnd;

    @Schema(description = "发起用户编号", example = "1024")
    private Long startUserId; // 注意，只有在【流程实例】菜单，才使用该参数

    @Schema(description = "动态表单字段查询 JSON Str", example = "{}")
    private String formFieldsParams; // SpringMVC 在 get 请求下，无法方便的定义 Map 类型的参数，所以通过 String 接收后，逻辑里面转换

}

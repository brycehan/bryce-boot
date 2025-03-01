package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 流程模型Dto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程模型Dto")
public class BpmModelDto extends BpmModelMetaInfoDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @NotEmpty(groups = UpdateGroup.class)
    private String id;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    @NotBlank(groups = {SaveGroup.class, UpdateGroup.class})
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 流程标识
     */
    @Schema(description = "流程标识")
    @Length(max = 30, groups = {SaveGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[a-zA-Z_][\\-.a-zA-Z0-9_$]*$", groups = {SaveGroup.class, UpdateGroup.class})
    private String key;

    /**
     * 流程分类
     */
    @Schema(description = "流程分类")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String category;

    /**
     * BPMN XML
     */
    @Schema(description = "BPMN XML")
    @Length(max = 640000, groups = {SaveGroup.class, UpdateGroup.class})
    private String bpmnXml;

    /**
     * Simple设计器模型数据
     */
    @Schema(description = "Simple设计器模型数据")
    @Valid
    private BpmSimpleModelNodeDto simpleModel;

}

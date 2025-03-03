package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class BpmModelPageDto extends BasePageDto {

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    @NotBlank(groups = {SaveGroup.class, UpdateGroup.class})
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 流程分类
     */
    @Schema(description = "流程分类")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String category;
}

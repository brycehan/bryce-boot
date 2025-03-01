package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 仿钉钉流程设计模型节点 Dto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@EqualsAndHashCode
@Schema(description = "仿钉钉流程设计模型节点 Dto")
public class BpmSimpleModelNodeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 模型节点
     */
    @Schema(description = "模型节点名称")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

}

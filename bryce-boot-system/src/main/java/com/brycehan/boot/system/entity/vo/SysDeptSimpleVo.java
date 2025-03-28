package com.brycehan.boot.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门精简信息 Vo
 *
 * @since 2025/3/27
 * @author Bryce Han
 */
@Data
@Schema(description = "部门精简信息 Vo")
public class SysDeptSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    @Schema(description = "部门编号", example = "102")
    private Long id;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称", example = "研发部")
    private String name;

    /**
     * 父部门 ID
     */
    @Schema(description = "父部门 ID", example = "1")
    private Long parentId;

}

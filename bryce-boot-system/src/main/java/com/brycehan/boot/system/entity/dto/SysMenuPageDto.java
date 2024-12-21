package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.enums.VisibleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统菜单PageDto
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统菜单PageDto")
public class SysMenuPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Size(max = 50)
    private String name;

    /**
     * 类型（C：目录，M：菜单，B：按钮）
     */
    @Schema(description = "类型（C：目录，M：菜单，B：按钮）")
    @Size(max = 1)
    private String type;

    /**
     * 可见性类型
     */
    @Schema(description = "可见性类型")
    private VisibleType visible;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

}

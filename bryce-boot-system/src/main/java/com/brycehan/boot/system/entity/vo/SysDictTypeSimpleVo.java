package com.brycehan.boot.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典类型精简信息 Vo
 *
 * @since 2025/4/15
 * @author Bryce Han
 */
@Data
@Schema(description = "字典类型精简信息 Vo")
public class SysDictTypeSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典类型编号
     */
    @Schema(description = "字典类型编号")
    private Long id;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

}

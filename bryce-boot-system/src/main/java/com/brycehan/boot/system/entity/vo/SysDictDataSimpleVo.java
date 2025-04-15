package com.brycehan.boot.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统字典数据简化 Vo
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Accessors(chain = true)
@Data
@Schema(description = "系统字典数据 Vo")
public class SysDictDataSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    private String dictLabel;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;

    /**
     * 标签属性
     * <br>
     * default、primary、success、info、warning、danger
     */
    @Schema(description = "标签属性")
    private String labelClass;

}

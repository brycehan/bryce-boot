package com.brycehan.boot.system.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统字典数据Vo
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Schema(description = "系统字典数据Vo")
@Data
public class SysDictDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

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
     * 字典类型
     */
    @Schema(description = "字典类型")
    private Long dictTypeId;

    /**
     * 标签属性
     */
    @Schema(description = "标签属性")
    private String labelClass;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Boolean status;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}

package com.brycehan.boot.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.brycehan.boot.common.enums.EnumTypeDescConverter;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统字典类型Vo
 *
 * @since 2023/09/06
 * @author Bryce Han
 */
@Data
@Schema(description = "系统字典类型Vo")
@ExcelIgnoreUnannotated
public class SysDictTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @ColumnWidth(20)
    @ExcelProperty(value = "字典编号")
    private Long id;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @ColumnWidth(20)
    @ExcelProperty(value = "字典类型")
    private String dictType;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @ColumnWidth(14)
    @ExcelProperty(value = "状态", converter = EnumTypeDescConverter.class)
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

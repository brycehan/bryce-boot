package com.brycehan.boot.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.brycehan.boot.common.enums.EnumTypeDescConverter;
import com.brycehan.boot.common.enums.ParamType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统参数Vo
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@Schema(description = "系统参数Vo")
@ExcelIgnoreUnannotated
public class SysParamVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @ColumnWidth(20)
    @ExcelProperty("参数编号")
    private Long id;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    @ColumnWidth(25)
    @ExcelProperty("参数名称")
    private String paramName;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    @ColumnWidth(30)
    @ExcelProperty("参数键名")
    private String paramKey;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    @ColumnWidth(14)
    @ExcelProperty("参数值")
    private String paramValue;

    /**
     * 参数类型（0：内置，0：应用）
     */
    @Schema(description = "参数类型（0：内置，0：应用）")
    @ColumnWidth(20)
    @ExcelProperty(value = "参数类型", converter = EnumTypeDescConverter.class)
    private ParamType paramType;

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

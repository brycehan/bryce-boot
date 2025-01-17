package com.brycehan.boot.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.brycehan.boot.common.enums.EnumDescConverter;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统岗位Vo
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@Schema(description = "系统岗位Vo")
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class SysPostVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "岗位名称")
    private String name;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @ColumnWidth(20)
    @ExcelProperty(value = "岗位编码")
    private String code;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @ColumnWidth(20)
    @ExcelProperty(value = "岗位排序")
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @ColumnWidth(14)
    @ExcelProperty(value = "状态", converter = EnumDescConverter.class)
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

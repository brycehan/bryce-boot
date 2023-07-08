package com.brycehan.boot.system.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.brycehan.boot.common.base.entity.BaseVo;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统岗位分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Schema(description = "系统岗位Vo")
@Getter
@Setter
@HeadFontStyle(fontHeightInPoints = 12)
@ContentFontStyle(fontHeightInPoints = 12, fontName = "宋体")
@ExcelIgnoreUnannotated
public class SysPostVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = AddGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private String id;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = "岗位编码", index = 1)
    @Size(max = 30, groups = {AddGroup.class, UpdateGroup.class})
    private String postCode;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = "岗位名称", index = 0)
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String postName;

    /**
     * 岗位排序
     */
    @Schema(description = "岗位排序")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = "岗位排序", index = 2)
    @Range(max = 2147483647, groups = {AddGroup.class, UpdateGroup.class})
    private Integer sortNumber;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    @ExcelProperty(value = "状态", index = 3)
    @Range(max = 1, message = "状态值只能是0或1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 300, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Null(groups = {AddGroup.class, UpdateGroup.class})
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}

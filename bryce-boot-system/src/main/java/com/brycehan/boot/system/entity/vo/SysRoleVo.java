package com.brycehan.boot.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.common.enums.EnumDescConverter;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色Vo
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@Schema(description = "系统角色Vo")
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class SysRoleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @ColumnWidth(20)
    @ExcelProperty(value = "角色名称")
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @ColumnWidth(20)
    @ExcelProperty(value = "角色编码")
    private String code;

    /**
     * 数据范围（0：全部数据，1：自定义数据，2：本部门及以下部门数据，3：本部门数据，4：本人数据）
     */
    @Schema(description = "数据范围（0：全部数据，1：自定义数据，2：本部门及以下部门数据，3：本部门数据，4：本人数据）")
    @ColumnWidth(20)
    @ExcelProperty(value = "数据范围", converter = EnumDescConverter.class)
    private DataScopeType dataScope;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @ColumnWidth(20)
    @ExcelProperty(value = "角色排序")
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
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 部门IDs
     */
    @Schema(description = "部门IDs")
    private List<Long> deptIds;

    /**
     * 菜单IDs
     */
    @Schema(description = "菜单IDs")
    private List<Long> menuIds;

}

package com.brycehan.boot.system.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.brycehan.boot.common.enums.DataScope;
import com.brycehan.boot.common.enums.EnumTypeDescConverter;
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
     * 数据范围（0：全部数据，1：本机构及以下机构数据，2：本机构数据，3：本人数据，4：自定义数据）
     */
    @Schema(description = "数据范围（0：全部数据，1：本机构及以下机构数据，2：本机构数据，3：本人数据，4：自定义数据）")
    @ColumnWidth(20)
    @ExcelProperty(value = "数据范围", converter = EnumTypeDescConverter.class)
    private DataScope dataScope;

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
    @ExcelProperty(value = "状态", converter = EnumTypeDescConverter.class)
    private StatusType status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 机构IDs
     */
    @Schema(description = "机构IDs")
    private List<Long> orgIds;

    /**
     * 菜单IDs
     */
    @Schema(description = "菜单IDs")
    private List<Long> menuIds;

}

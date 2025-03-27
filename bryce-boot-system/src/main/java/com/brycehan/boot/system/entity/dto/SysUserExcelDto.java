package com.brycehan.boot.system.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.brycehan.boot.common.enums.EnumDescConverter;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.StatusType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户 ExcelDto
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@ExcelIgnoreUnannotated
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class SysUserExcelDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @NotNull
    @ExcelProperty(value = "部门编号")
    @ColumnWidth(20)
    private Long deptId;

    /**
     * 账号
     */
    @NotBlank
    @Length(min = 1, max = 50)
    @ExcelProperty(value = "账号")
    @ColumnWidth(14)
    private String username;

    /**
     * 姓名
     */
    @NotBlank
    @Length(min = 1, max = 50)
    @ExcelProperty(value = "姓名")
    @ColumnWidth(14)
    private String nickname;

    /**
     * 邮箱
     */
    @Email
    @NotBlank
    @Length(min = 1, max = 50)
    @ExcelProperty(value = "用户邮箱")
    @ColumnWidth(14)
    private String email;

    /**
     * 手机号码
     */
    @NotBlank
    @Length(min = 1, max = 20)
    @ExcelProperty(value = "手机号码")
    @ColumnWidth(20)
    private String phone;

    /**
     * 性别
     */
    @NotNull
    @ExcelProperty(value = "性别", converter = EnumDescConverter.class)
    @ColumnWidth(14)
    private GenderType gender;

    /**
     * 状态
     */
    @NotNull
    @ExcelProperty(value = "账号状态", converter = EnumDescConverter.class)
    @ColumnWidth(14)
    private StatusType status;

}

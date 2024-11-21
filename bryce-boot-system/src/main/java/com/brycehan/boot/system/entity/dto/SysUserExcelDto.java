package com.brycehan.boot.system.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.brycehan.boot.common.enums.EnumTypeDescConverter;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.StatusType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户 ExcelDto
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@ExcelIgnoreUnannotated
public class SysUserExcelDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @ExcelProperty(value = "账号")
    private String username;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String nickname;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", converter = EnumTypeDescConverter.class)
    private GenderType gender;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 机构ID
     */
    @ExcelProperty(value = "机构ID")
    private Long orgId;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = EnumTypeDescConverter.class)
    private StatusType status;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createdTime;

}

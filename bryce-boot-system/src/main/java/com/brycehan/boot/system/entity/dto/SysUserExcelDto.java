package com.brycehan.boot.system.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fhs.core.trans.anno.UnTrans;
import com.fhs.core.trans.constant.UnTransType;
import com.fhs.core.trans.vo.TransPojo;
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
public class SysUserExcelDto implements Serializable, TransPojo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     * <p>本属性对于导入无用，只是用于翻译</p>
     */
    private Long id;

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
     * 性别（M：男, F：女，N：未知）
     */
    @UnTrans(type = UnTransType.DICTIONARY, dict = "sys_user_gender", refs = {"genderLabel"})
    private String gender;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String genderLabel;

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
     * 状态（0：停用，1：正常）
     */
    @UnTrans(type = UnTransType.DICTIONARY, dict = "sys_status", refs = "statusLabel")
    private Boolean status;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String statusLabel;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createdTime;

}

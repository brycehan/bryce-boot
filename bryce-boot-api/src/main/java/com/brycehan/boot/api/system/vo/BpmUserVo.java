package com.brycehan.boot.api.system.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Bpm 用户 Vo
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
@Data
@Schema(description = "Bpm 用户 Vo")
public class BpmUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 性别（M：男，F：女，N：未知）
     */
    @Schema(description = "性别（M：男，F：女，N：未知）")
    private GenderType gender;

    /**
     * 用户类型（0：系统用户）
     */
    @Schema(description = "用户类型（0：系统用户）")
    private Integer type;

    /**
     * 手机号码
     */
    @ColumnWidth(14)
    @ExcelProperty(value = "手机号码")
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "邮箱")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称", example = "研发部")
    private String deptName;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 岗位IDs
     */
    @Schema(description = "岗位ID")
    private List<Long> postIds;
}

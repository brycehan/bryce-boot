package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统用户PageDto
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户PageDto")
public class SysUserPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Length(max = 50)
    private String username;

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
    @Schema(description = "手机号码")
    @Length(max = 20)
    private String phone;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createdTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createdTimeEnd;

}

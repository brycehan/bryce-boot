package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;

/**
 * 系统岗位分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Schema(description = "系统岗位Dto")
@Getter
@Setter
public class SysPostDto extends BaseDto {

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
    @Size(max = 30, groups = {AddGroup.class, UpdateGroup.class})
    private String postCode;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    @Size(max = 50, groups = {AddGroup.class, UpdateGroup.class})
    private String postName;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @Range(max = 2147483647, groups = {AddGroup.class, UpdateGroup.class})
    private Integer sortNumber;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    @Range(max = 1, message = "状态值只能是0或1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 300, groups = {AddGroup.class, UpdateGroup.class})
    private String remark;

}

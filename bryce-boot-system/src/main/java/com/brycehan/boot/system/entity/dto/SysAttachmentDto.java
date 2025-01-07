package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统附件Dto
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统附件Dto")
public class SysAttachmentDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    @Length(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 附件地址
     */
    @Schema(description = "附件地址")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    @Length(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String suffix;

    /**
     * 访问类型
     */
    @Schema(description = "访问类型")
    private AccessType accessType;

    /**
     * 附件大小（单位字节）
     */
    @Schema(description = "附件大小（单位字节）")
    private Long size;

    /**
     * 哈希码
     */
    @Schema(description = "哈希码")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String hash;

    /**
     * 存储平台
     */
    @Schema(description = "存储平台")
    @Length(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String platform;

}

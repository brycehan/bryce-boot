package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统附件Dto
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@Schema(description = "系统附件Dto")
public class SysAttachmentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Schema(description = "ID")
    private Long id;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    @Size(max = 100, groups = {SaveGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 附件地址
     */
    @Schema(description = "附件地址")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 附件大小（单位字节）
     */
    @Schema(description = "附件大小（单位字节）")
    private Long size;

    /**
     * 附件类型
     */
    @Schema(description = "附件类型")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String suffix;

    /**
     * 哈希码
     */
    @Schema(description = "哈希码")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String hash;

    /**
     * 存储平台
     */
    @Schema(description = "存储平台")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String platform;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}

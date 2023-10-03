package com.brycehan.boot.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统附件Vo
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@Schema(description = "系统附件Vo")
public class SysAttachmentVo implements Serializable {

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
    private String name;

    /**
     * 附件地址
     */
    @Schema(description = "附件地址")
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
    private String type;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    private String suffix;

    /**
     * 哈希码
     */
    @Schema(description = "哈希码")
    private String hash;

    /**
     * 存储平台
     */
    @Schema(description = "存储平台")
    private String platform;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

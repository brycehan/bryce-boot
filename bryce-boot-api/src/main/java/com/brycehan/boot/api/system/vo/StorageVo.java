package com.brycehan.boot.api.system.vo;

import com.brycehan.boot.common.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 存储 vo
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Data
@Tag(name = "存储 vo")
public class StorageVo {

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String name;

    /**
     * 附件路径
     */
    @Schema(description = "附件路径")
    private String path;

    /**
     * 附件地址
     */
    @Schema(description = "附件地址")
    private String url;

    /**
     * 附件名后缀
     */
    @Schema(description = "附件名后缀")
    private String suffix;

    /**
     * 访问类型
     */
    @Schema(description = "访问类型")
    private AccessType accessType;

    /**
     * 文件大小（单位字节）
     */
    @Schema(description = "文件大小（单位字节）")
    private Long size;

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
}

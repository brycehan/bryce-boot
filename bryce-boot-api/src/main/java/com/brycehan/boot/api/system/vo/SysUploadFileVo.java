package com.brycehan.boot.api.system.vo;

import com.brycehan.boot.common.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 上传文件Vo
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Data
@Schema(description = "上传文件Vo")
public class SysUploadFileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String name;

    /**
     * 文件地址
     */
    @Schema(description = "文件地址")
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

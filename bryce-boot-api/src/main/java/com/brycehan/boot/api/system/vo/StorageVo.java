package com.brycehan.boot.api.system.vo;

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
     * URL
     */
    @Schema(description = "URL")
    private String url;

    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    private Long size;
}

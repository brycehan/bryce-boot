package com.brycehan.boot.system.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 上传文件表Vo
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Data
@TableName("brc_sys_upload_file")
@Schema(description = "上传文件表Vo")
public class SysUploadFileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 文件原始名称
     */
    @Schema(description = "文件原始名称")
    private String oldName;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    private String newPath;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件名后缀
     */
    @Schema(description = "文件名后缀")
    private String suffix;

    /**
     * 哈希码
     */
    @Schema(description = "哈希码")
    private String hash;

    /**
     * 文件大小（单位字节）
     */
    @Schema(description = "文件大小（单位字节）")
    private Long size;

    /**
     * 宽
     */
    @Schema(description = "宽")
    private Integer width;

    /**
     * 高
     */
    @Schema(description = "高")
    private Integer height;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private String lng;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private String lat;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private String tags;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 版本
     */
    @Schema(description = "版本")
    private Integer version;

    /**
     * 创建用户ID
     */
    @Schema(description = "创建用户ID")
    private Long createdUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}

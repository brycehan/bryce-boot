package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 上传文件分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Getter
@Setter
@Schema(description = "SysUploadFilePageDto对象")
public class SysUploadFilePageDto extends BasePageDto {

    private static final long serialVersionUID = 1L;


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 文件原始名称
     */
    @Size(max = 100, groups = QueryGroup.class)
    @Schema(description = "文件原始名称")
    private String oldName;

    /**
     * 文件路径
     */
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "文件路径")
    private String newPath;

    /**
     * 文件类型
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件名后缀
     */
    @Size(max = 10, groups = QueryGroup.class)
    @Schema(description = "文件名后缀")
    private String suffix;

    /**
     * 哈希码
     */
    @Size(max = 255, groups = QueryGroup.class)
    @Schema(description = "哈希码")
    private String hash;

    /**
     * 文件大小（单位字节）
     */
    @Schema(description = "文件大小（单位字节）")
    private Long size1;

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
     * 纬度
     */
    @Size(max = 30, groups = QueryGroup.class)
    @Schema(description = "纬度")
    private String lat;

    /**
     * 经度
     */
    @Size(max = 30, groups = QueryGroup.class)
    @Schema(description = "经度")
    private String lng;

    /**
     * 标签
     */
    @Size(max = 255, groups = QueryGroup.class)
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
     * 创建用户账号
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "创建用户账号")
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
}

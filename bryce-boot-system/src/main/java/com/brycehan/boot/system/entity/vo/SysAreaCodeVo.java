package com.brycehan.boot.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地区编码 Vo
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Data
@Schema(description = "地区编码 Vo")
public class SysAreaCodeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Integer id;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Integer parentId;

    /**
     * 层级
     */
    @Schema(description = "层级")
    private Integer deep;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 拼音前缀
     */
    @Schema(description = "拼音前缀")
    private String pinyinPrefix;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    private String pinyin;

    /**
     * 扩展ID
     */
    @Schema(description = "扩展ID")
    private String extId;

    /**
     * 扩展名称
     */
    @Schema(description = "扩展名称")
    private String extName;

}

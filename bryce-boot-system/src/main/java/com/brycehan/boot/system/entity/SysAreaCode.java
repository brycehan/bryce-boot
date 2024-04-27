package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 地区编码entity
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Data
@TableName("brc_sys_area_code")
public class SysAreaCode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 层级
     */
    private Integer deep;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 拼音前缀
     */
    private String pinyinPrefix;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 扩展ID
     */
    private String extId;

    /**
     * 扩展名称
     */
    private String extName;

}

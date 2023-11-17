package com.brycehan.boot.monitor.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 磁盘信息
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Data
public class Disk implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 磁盘名称
     */
    private String diskName;

    /**
     * 磁盘类型
     */
    private String diskType;

    /**
     * 磁盘路径
     */
    private String dirName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private String usage;

}

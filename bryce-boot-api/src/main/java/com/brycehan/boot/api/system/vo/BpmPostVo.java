package com.brycehan.boot.api.system.vo;

import com.brycehan.boot.common.enums.StatusType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Bpm 岗位 Vo
 *
 * @since 2025/3/28
 * @author Bryce Han
 */
@Data
public class BpmPostVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 岗位序号
     */
    private Long id;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位排序
     */
    private Integer sort;
    /**
     * 状态
     * <br>
     * 枚举 {@link StatusType}
     */
    private Integer status;

}

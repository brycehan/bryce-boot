package com.brycehan.boot.bpm.entity.vo;

import lombok.Data;

/**
 * 流程表单字段 Vo
 *
 * @since 2025/3/16
 * @author Bryce Han
 */
@Data
public class BpmFormFieldVo {

    /**
     * 字段类型
     */
    private String type;
    /**
     * 字段标识
     */
    private String field;
    /**
     * 字段标题
     */
    private String title;

}

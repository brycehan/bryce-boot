package com.brycehan.boot.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据范围
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Data
@AllArgsConstructor
public class DataScope {

    /**
     * sql过滤
     */
    private String sqlFilter;

}

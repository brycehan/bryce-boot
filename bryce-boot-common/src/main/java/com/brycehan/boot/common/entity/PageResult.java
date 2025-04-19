package com.brycehan.boot.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果
 *
 * @since 2023/4/10
 * @author Bryce Han
 */
@Data
@AllArgsConstructor
@Schema(description = "分页结果数据")
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final PageResult<?> EMPTY = new PageResult<>(Collections.emptyList(), 0);

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private List<T> list;

    /**
     * 总条数
     */
    @Schema(description = "总条数")
    private long total;


    @SuppressWarnings("unchecked")
    public static <T> PageResult<T> empty() {
        return (PageResult<T>) EMPTY;
    }

    /**
     * 构造方法
     *
     * @param total 总条数
     * @param list  列表数据
     */
    public static <T> PageResult<T> of(List<T> list, long total) {
        if (total == 0) {
            return empty();
        }
        return new PageResult<>(list, total);
    }
}

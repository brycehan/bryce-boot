package com.brycehan.boot.common.base.excel;

import java.util.List;

/**
 * Excel 读取数据完成回调
 *
 * @author Bryce Han
 * @since 2023/11/22
 */
@FunctionalInterface
public interface ExcelFinishCallback <T> {

    default void doAfterAllAnalysed(List<T> list) {
        doSaveBatch(list);
    }

    void doSaveBatch(List<T> list);
}

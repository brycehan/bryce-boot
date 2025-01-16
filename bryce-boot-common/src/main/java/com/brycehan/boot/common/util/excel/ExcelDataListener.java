package com.brycehan.boot.common.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Excel 读取监听器
 *
 * @author Bryce Han
 * @since 2023/11/22
 */
public class ExcelDataListener<T> extends AnalysisEventListener<T> {

    /**
     * 临时保存 Excel 记录的集合
     */
    private final List<T> list = new LinkedList<>();

    /**
     * 回调接口
     */
    private final ExcelFinishCallback<T> callback;

    public ExcelDataListener(ExcelFinishCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * 这个每一条数据解析都会来调用，在这里可以做一些其它的操作（过滤，分批入库...）就靠自己去拓展了
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
        if(list.size() == 500) {
            callback.doSaveBatch(list);
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了，都会来调用
     * <p>
     * 这里也要保存数据，确保最后遗留的数据也存储到数据库
     * </p>
     *
     * @param context analysis context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        callback.doAfterAllAnalysed(this.list);
    }

}

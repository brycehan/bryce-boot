package com.brycehan.boot.framework.operationlog.annotation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 操作类型
 *
 * @author brycehan
 * @since 2023/8/28
 */
@Getter
@RequiredArgsConstructor
public enum OperateType {

    /**
     * 新增
     */
    INSERT("新增"),

    /**
     * 修改
     */
    UPDATE("修改"),

    /**
     * 删除
     */
    DELETE("删除"),

    /**
     * 查询
     */
    GET("查询"),

    /**
     * 导入
     */
    IMPORT("导入"),

    /**
     * 导出
     */
    EXPORT("导出"),

    /**
     * 授权
     */
    GRANT("授权"),

    /**
     * 强退
     */
    FORCE_QUIT("强退"),

    /**
     * 生成代码
     */
    GEN_CODE("生成代码"),

    /**
     * 清空数据
     */
    CLEAN_DATA("清空数据"),

    /**
     * 其它
     */
    OTHER("其它");

    private final String value;

}

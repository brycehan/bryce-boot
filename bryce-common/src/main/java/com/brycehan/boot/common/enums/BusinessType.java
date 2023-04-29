package com.brycehan.boot.common.enums;

/**
 * 业务操作类型
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
public enum BusinessType {

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 强退
     */
    FORCE_QUIT,

    /**
     * 生成代码
     */
    GEN_CODE,

    /**
     * 清空数据
     */
    CLEAN_DATA,

    /**
     * 其它
     */
    OTHER

}

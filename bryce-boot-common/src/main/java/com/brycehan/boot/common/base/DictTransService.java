package com.brycehan.boot.common.base;

import java.util.Map;

/**
 * 字典翻译服务
 *
 * @author Bryce Han
 * @since 2024/11/3
 */
public interface DictTransService {

    /**
     * 获取字典翻译map
     *
     * @return 字典翻译map
     */
    Map<String, String> getDictTransMap();

    /**
     * 设置字典翻译map
     *
     * @param key 键
     * @param value 值
     */
    default void set(String key, String value) {
        getDictTransMap().put(key, value);
    }

}

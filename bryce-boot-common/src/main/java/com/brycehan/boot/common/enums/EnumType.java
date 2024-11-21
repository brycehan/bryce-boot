package com.brycehan.boot.common.enums;

import java.io.Serializable;

/**
 * 枚举类型接口
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
public interface EnumType {

    /**
     * 获取枚举值
     * @return 枚举值
     */
    Serializable getValue();

    /**
     * 获取枚举描述
     * @return 枚举描述
     */
    String getDesc();

}

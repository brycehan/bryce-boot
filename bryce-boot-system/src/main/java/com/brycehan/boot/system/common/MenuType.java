package com.brycehan.boot.system.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 菜单类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@RequiredArgsConstructor
public enum MenuType {
    /**
     * 菜单
     */
    MENU("M"),
    /**
     * 按钮
     */
    BUTTON("B"),
    /**
     * 接口
     */
    INTERFACE("I");

    private final String value;

}

package com.brycehan.boot.system.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 菜单类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum MenuType {

    MENU("M", "菜单"),
    BUTTON("B", "按钮"),
    CATALOG("C", "目录"),
    ;

    /**
     * 类型值
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    @DescValue
    private final String desc;

    /**
     * 根据类型值获取类型
     *
     * @param value 类型值
     * @return 类型
     */
    public static MenuType of(String value) {
        for (MenuType menuType : MenuType.values()) {
            if (menuType.getValue().equals(value)) {
                return menuType;
            }
        }
        return null;
    }

    /**
     * 根据描述获取类型
     *
     * @param desc 描述
     * @return 类型
     */
    public static MenuType getByDesc(String desc) {
        for (MenuType menuType : MenuType.values()) {
            if (menuType.getDesc().equals(desc)) {
                return menuType;
            }
        }
        return null;
    }
}

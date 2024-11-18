package com.brycehan.boot.system.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
public enum MenuType {
    /**
     * 菜单
     */
    MENU("M", "菜单"),
    /**
     * 按钮
     */
    BUTTON("B", "按钮"),
    /**
     * 接口
     */
    INTERFACE("I", "接口");

    @EnumValue
    @JsonValue
    private final String value;
    private final String desc;

    MenuType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}

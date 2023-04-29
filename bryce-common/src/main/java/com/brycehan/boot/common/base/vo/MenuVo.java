package com.brycehan.boot.common.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 路由配置vo
 *
 * @author Bryce Han
 * @since 2022/10/21
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo {

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型（D：目录，M：菜单，B：按钮）
     */
    private String menuType;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 子路由
     */
    private List<MenuVo> routes;

}

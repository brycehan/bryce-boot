package com.brycehan.boot.common.entity.vo;

import com.brycehan.boot.common.enums.DataScopeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 路由配置vo
 *
 * @since 2022/10/21
 * @author Bryce Han
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleVo {

    /**
     * ID
     */
    private Long id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 数据范围
     */
    @Schema(description = "数据范围")
    private DataScopeType dataScope;

    /**
     * 菜单权限集合
     */
    private Set<String> authoritySet;

}

package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.SysRoleMenu;

import java.util.List;

/**
 * 系统角色菜单关系服务
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

    /**
     * 保存或修改
     *
     * @param roleId  角色ID
     * @param menuIds 菜单IDs
     */
    void saveOrUpdate(Long roleId, List<Long> menuIds);

    /**
     * 根据角色ID，查询拥有的菜单IDs
     *
     * @param roleId 角色ID
     * @return 拥有的菜单IDs
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色IDs，删除角色菜单关系
     *
     * @param roleIds 角色IDs
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据菜单IDs，删除角色菜单关系
     *
     * @param menuIds 菜单IDs
     */
    void deleteByMenuIds(List<Long> menuIds);

}

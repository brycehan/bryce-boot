package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.entity.SysRoleMenu;

import java.util.List;

/**
 * 系统角色菜单中间表服务类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色ID列表查询拥有的菜单ID列表
     *
     * @param roleIdList 角色ID列表
     * @return 拥有的菜单ID列表
     */
    List<Long> getMenuIdListByRoleIdList(List<Long> roleIdList);
}

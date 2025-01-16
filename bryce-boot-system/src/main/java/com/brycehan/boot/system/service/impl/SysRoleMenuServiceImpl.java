package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.system.entity.po.SysRoleMenu;
import com.brycehan.boot.system.mapper.SysRoleMenuMapper;
import com.brycehan.boot.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 系统角色菜单关系服务实现
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIds) {
        // 数据库用户角色IDs
        List<Long> dbMenuIds = getMenuIdsByRoleId(roleId);

        // 需要新增的菜单IDs
        Collection<Long> insertMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        if (CollUtil.isNotEmpty(insertMenuIds)) {
            List<SysRoleMenu> list = insertMenuIds.stream().map(menuId -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setId(IdGenerator.nextId());
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                return roleMenu;
            }).toList();

            // 批量新增
            this.saveBatch(list);
        }

        // 需要删除的菜单IDs
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
            queryWrapper.in(SysRoleMenu::getMenuId, deleteMenuIds);

            this.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);

        List<SysRoleMenu> sysRoleMenus = baseMapper.selectList(queryWrapper);

        return sysRoleMenus.stream().map(SysRoleMenu::getMenuId)
                .toList();
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        baseMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds));
    }

    @Override
    public void deleteByMenuIds(List<Long> menuIds) {
        baseMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getMenuId, menuIds));
    }

}

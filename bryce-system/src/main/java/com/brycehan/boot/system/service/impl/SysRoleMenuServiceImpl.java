package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.system.entity.SysRoleMenu;
import com.brycehan.boot.system.mapper.SysRoleMenuMapper;
import com.brycehan.boot.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色菜单中间表服务实现类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    public SysRoleMenuServiceImpl(SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    /**
     * 根据角色ID列表查询拥有的菜单ID列表
     *
     * @param roleIdList 角色ID列表
     * @return 菜单ID列表
     */
    @Override
    public List<String> getMenuIdListByRoleIdList(List<String> roleIdList) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(!CollectionUtils.isEmpty(roleIdList), "role_id", roleIdList);
        List<SysRoleMenu> sysRoleMenuList = this.sysRoleMenuMapper.selectList(queryWrapper);
        return sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }
}

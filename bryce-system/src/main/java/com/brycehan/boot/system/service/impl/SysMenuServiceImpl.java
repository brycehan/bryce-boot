package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.util.TreeUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.convert.MenuConvert;
import com.brycehan.boot.system.convert.SysMenuConvert;
import com.brycehan.boot.system.dto.SysMenuDto;
import com.brycehan.boot.system.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.mapper.SysMenuMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysRoleMenuService;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.vo.SysMenuVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
@AllArgsConstructor
 public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    private final SysUserRoleService sysUserRoleService;

    private final SysRoleMenuService sysRoleMenuService;

    @Override
    public void save(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        sysMenu.setId(IdGenerator.nextId());
        this.sysMenuMapper.insert(sysMenu);
    }

    @Override
    public void update(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        this.sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public PageResult<SysMenuVo> page(SysMenuPageDto sysMenuPageDto) {

        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(sysMenuPageDto.getName()), SysMenu::getName, sysMenuPageDto.getName());
        wrapper.eq(Objects.nonNull(sysMenuPageDto.getStatus()), SysMenu::getStatus, sysMenuPageDto.getStatus());

        IPage<SysMenu> page =  this.sysMenuMapper.selectPage(getPage(sysMenuPageDto), wrapper);

        return new PageResult<>(page.getTotal(), SysMenuConvert.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public Set<String> findAuthorityByUserId(Long userId) {
        return this.sysMenuMapper.findAuthorityByUserId(userId);
    }

    @Override
    public List<SysMenu> getSysMenuListByUserId(Long userId) {
        if (Objects.nonNull(userId)) {
            List<Long> roleIds = this.sysUserRoleService.getRoleIdListByUserId(userId);
            List<Long> menuIds = this.sysRoleMenuService.getMenuIdListByRoleIdList(roleIds);
            List<SysMenu> sysMenus = this.getSysMenusByMenuIds(menuIds);
            // 将ID和菜单绑定
            Map<Long, SysMenu> sysMenuMap = new HashMap<>();
            sysMenus.forEach(sysMenu -> sysMenuMap.put(sysMenu.getId(), sysMenu));
            // 按层级关系组装菜单
            Iterator<SysMenu> iterator = sysMenus.iterator();
            while (iterator.hasNext()) {
                SysMenu sysMenu = iterator.next();
                SysMenu parent = sysMenuMap.get(sysMenu.getParentId());
                if (Objects.nonNull(parent)) {
                    parent.getChildren().add(sysMenu);
                    // 删除非根当前节点
                    iterator.remove();
                }
            }
            return sysMenus;
        }
        return Collections.emptyList();
    }

    /**
     * 根据菜单ID列表查询系统其它菜单列表
     *
     * @param menuIds 菜单ID列表
     * @return 菜单列表
     */
    private List<SysMenu> getSysMenusByMenuIds(List<Long> menuIds) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(!CollectionUtils.isEmpty(menuIds), "id", menuIds)
                .eq("non_locked", DataConstants.ENABLE);
        return this.sysMenuMapper.selectList(queryWrapper);
    }

    @Override
    public List<MenuVo> buildMenus(List<SysMenuVo> menus) {
        return menus.stream()
                .map(menu -> {
                    MenuVo route = MenuConvert.INSTANCE.convert(menu);
                    List<SysMenuVo> children = menu.getChildren();
                    // 1、菜单是目录类型时，并且有子菜单
                    if (UserConstants.TYPE_DIR.equals(menu.getType()) && !CollectionUtils.isEmpty(children)) {
                        route.setRoutes(buildMenus(children));
                    }

                    return route;
                }).toList();
    }

    @Override
    public List<SysMenuVo> getMenuTreeList(LoginUser loginUser, String type) {
        List<SysMenu> menuList;

        if(loginUser.getSuperAdmin()){
            // 超级管理员菜单处理
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getStatus, DataConstants.ENABLE);
            queryWrapper.eq(StringUtils.isNotEmpty(type) ,SysMenu::getType, type);
            queryWrapper.orderByAsc(Arrays.asList(SysMenu::getParentId, SysMenu::getSort));

            menuList = this.sysMenuMapper.selectList(queryWrapper);
        }else {
            // 普通用户菜单处理
            menuList = this.sysMenuMapper.selectMenuTreeList(loginUser.getId(), type);
        }

        return TreeUtils.build(SysMenuConvert.INSTANCE.convert(menuList));
    }

}

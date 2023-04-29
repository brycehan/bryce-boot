package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.HttpResponseStatusEnum;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.SecurityUtils;
import com.brycehan.boot.system.convert.MenuConvert;
import com.brycehan.boot.system.convert.SysMenuConvert;
import com.brycehan.boot.system.dto.DeleteDto;
import com.brycehan.boot.system.dto.SysMenuDto;
import com.brycehan.boot.system.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.mapper.SysMenuMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysRoleMenuService;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.vo.SysMenuVo;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        sysMenu.setId(IdGenerator.generate());
        this.sysMenuMapper.insert(sysMenu);
    }

    @Override
    public void update(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        this.sysMenuMapper.updateById(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteDto deleteDto) {
        // 过滤空数据
        List<String> ids = deleteDto.getIds()
                .stream()
                .filter(StringUtils::isNotBlank)
                .toList();
        if(!CollectionUtils.isEmpty(ids)){
            throw BusinessException.responseStatus(HttpResponseStatusEnum.HTTP_BAD_REQUEST);
        }
        // 删除菜单
        removeBatchByIds(ids);
        // 删除

    }


    @Override
    public PageResult<SysMenuVo> page(SysMenuPageDto sysMenuPageDto) {

        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(sysMenuPageDto.getMenuName()), SysMenu::getMenuName, sysMenuPageDto.getMenuName());
        wrapper.eq(Objects.nonNull(sysMenuPageDto.getStatus()), SysMenu::getStatus, sysMenuPageDto.getStatus());

        IPage<SysMenu> page =  this.sysMenuMapper.selectPage(getPage(sysMenuPageDto), wrapper);

        return new PageResult<>(page.getTotal(), SysMenuConvert.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public Set<String> selectMenuPermissionByUserId(String userId) {
        return this.sysMenuMapper.selectMenuPermissionByUserId(userId);
    }

    @Override
    public List<SysMenu> getSysMenuListByUserId(String userId) {
        if (Objects.nonNull(userId)) {
            List<String> roleIds = this.sysUserRoleService.getRoleIdListByUserId(userId);
            List<String> menuIds = this.sysRoleMenuService.getMenuIdListByRoleIdList(roleIds);
            List<SysMenu> sysMenus = this.getSysMenusByMenuIds(menuIds);
            // 将ID和菜单绑定
            Map<String, SysMenu> sysMenuMap = new HashMap<>();
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
    private List<SysMenu> getSysMenusByMenuIds(List<String> menuIds) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(!CollectionUtils.isEmpty(menuIds), "id", menuIds)
                .eq("non_locked", DataConstants.ENABLE);
        return this.sysMenuMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysMenuVo> selectMenuTreeByUserId(String userId) {
        List<SysMenu> menus;

        if(SecurityUtils.isAdmin(userId)){
            // 1、管理员菜单处理
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getStatus, DataConstants.ENABLE);
            queryWrapper.in(SysMenu::getMenuType, Sets.newHashSet("D", "M"));
            queryWrapper.orderByAsc(Arrays.asList(SysMenu::getParentId, SysMenu::getSortNumber));
             menus = this.sysMenuMapper.selectList(queryWrapper);
        }else {
            // 2、普通用户菜单处理
            menus = this.sysMenuMapper.selectMenuTreeByUserId(userId);
        }

        return SysMenuConvert.INSTANCE.convert(formatTree(menus));
    }

    @Override
    public List<MenuVo> buildMenus(List<SysMenuVo> menus) {
        return menus.stream()
                .map(menu -> {
                    MenuVo route = MenuConvert.INSTANCE.convert(menu);
                    List<SysMenuVo> children = menu.getChildren();
                    // 1、菜单是目录类型时，并且有子菜单
                    if (UserConstants.TYPE_DIR.equals(menu.getMenuType()) && !CollectionUtils.isEmpty(children)) {
                        route.setRoutes(buildMenus(children));
                    }

                    return route;
                }).toList();
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param menus 菜单列表
     * @return 格式化后树型菜单列表
     */
    private List<SysMenu> formatTree(List<SysMenu> menus) {
        // 1、获取子节点
        List<SysMenu> sysMenus = menus.stream()
                .filter(sysMenu -> DataConstants.TREE_ROOT_ID.equals(sysMenu.getParentId()))
                .toList();
        // 2、递归处理子节点
        sysMenus.forEach(menu -> recursionChildren(menus, menu));
        return sysMenus;
    }

    /**
     * 递归处理子节点
     *
     * @param list    所有节点列表
     * @param sysMenu 当前菜单
     */
    private void recursionChildren(List<SysMenu> list, SysMenu sysMenu) {
        // 1、获取子节点
        List<SysMenu> children = getChildren(list, sysMenu);
        sysMenu.setChildren(children);
        // 2、子节点递归处理
        for (SysMenu child : children) {
            if (hasChild(list, child)) {
                recursionChildren(list, child);
            }
        }
    }

    /**
     * 获取当前菜单的子节点列表
     *
     * @param list    所有节点列表
     * @param sysMenu 当前菜单
     * @return 菜单的子节点列表
     */
    private List<SysMenu> getChildren(List<SysMenu> list, SysMenu sysMenu) {
        return list.stream()
                .filter(menu -> menu.getParentId().equals(sysMenu.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 判断当前菜单是否有子节点
     *
     * @param list    所有节点列表
     * @param sysMenu 当前菜单
     * @return 是否有子节点状态
     */
    private boolean hasChild(List<SysMenu> list, SysMenu sysMenu) {
        return list.stream()
                .anyMatch(menu -> menu.getParentId().equals(sysMenu.getId()));
    }

}

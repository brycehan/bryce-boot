package com.brycehan.boot.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.common.util.TreeUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysMenuConvert;
import com.brycehan.boot.system.entity.dto.SysMenuAuthorityDto;
import com.brycehan.boot.system.entity.dto.SysMenuDto;
import com.brycehan.boot.system.entity.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.po.SysMenu;
import com.brycehan.boot.system.entity.vo.SysMenuVo;
import com.brycehan.boot.system.mapper.SysMenuMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统菜单服务实现
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuService sysRoleMenuService;

     /**
     * 添加系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     */
    public void save(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        sysMenu.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysMenu);
    }

    /**
     * 更新系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     */
    @Override
    public void update(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        // 上级菜单不能为自己
        if (sysMenu.getId().equals(sysMenu.getParentId())) {
            throw new RuntimeException("上级菜单不能为自己");
        }

        // 更新菜单
        this.baseMapper.updateById(sysMenu);
    }

    @Override
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull)
                .toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 删除菜单
        this.baseMapper.deleteByIds(ids);

        // 删除角色菜单关系
        this.sysRoleMenuService.deleteByMenuIds(ids);
    }

    @Override
    public PageResult<SysMenuVo> page(SysMenuPageDto sysMenuPageDto) {
        IPage<SysMenu> page = this.baseMapper.selectPage(sysMenuPageDto.toPage(), getWrapper(sysMenuPageDto));
        return new PageResult<>(page.getTotal(), SysMenuConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysMenuPageDto 系统菜单分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysMenu> getWrapper(SysMenuPageDto sysMenuPageDto) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(sysMenuPageDto.getName()), SysMenu::getName, sysMenuPageDto.getName());
        wrapper.eq(StringUtils.isNotBlank(sysMenuPageDto.getType()), SysMenu::getType, sysMenuPageDto.getType());
        wrapper.eq(Objects.nonNull(sysMenuPageDto.getStatus()), SysMenu::getStatus, sysMenuPageDto.getStatus());

        return wrapper;
    }

    @Override
    public void export(SysMenuPageDto sysMenuPageDto) {
        List<SysMenu> sysMenuList = this.baseMapper.selectList(getWrapper(sysMenuPageDto));
        List<SysMenuVo> sysMenuVoList = SysMenuConvert.INSTANCE.convert(sysMenuList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysMenuVo.class, "系统菜单_" + today, "系统菜单", sysMenuVoList);
    }

    @Override
    public List<SysMenuVo> list(SysMenuDto sysMenuDto) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysMenuDto.getName()), SysMenu::getName, sysMenuDto.getName());
        queryWrapper.eq(Objects.nonNull(sysMenuDto.getStatus()), SysMenu::getStatus, sysMenuDto.getStatus());
        queryWrapper.orderByAsc(SysMenu::getSort);
        List<SysMenu> list = this.baseMapper.selectList(queryWrapper);

        return TreeUtils.build(SysMenuConvert.INSTANCE.convert(list), 0L);
    }

    @Override
    public List<SysMenuVo> getMenuTreeList(LoginUser loginUser, String type) {
        List<SysMenu> menuList;

        if (loginUser.isSuperAdmin()) {
            // 超级管理员菜单处理
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getStatus, StatusType.ENABLE.value());
            queryWrapper.eq(StringUtils.isNotEmpty(type), SysMenu::getType, type);
            queryWrapper.orderByAsc(Arrays.asList(SysMenu::getParentId, SysMenu::getSort));

            menuList = this.baseMapper.selectList(queryWrapper);
        } else {
            // 普通用户菜单处理
            menuList = this.baseMapper.selectMenuTreeList(loginUser.getId(), type);
        }

        return TreeUtils.build(SysMenuConvert.INSTANCE.convert(menuList));
    }

    @Override
    public Long getSubMenuCount(List<Long> parentIds) {
        List<Long> realParentIds = parentIds.stream()
                .filter(Objects::nonNull)
                .toList();

        if (CollectionUtils.isEmpty(realParentIds)) {
            return 0L;
        }

        return count(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getParentId, realParentIds));
    }

    @Override
    public Set<String> findAuthority(LoginUser loginUser) {
        // 超级管理员，拥有最高权限
        Set<String> authoritySet;
        if (loginUser.isSuperAdmin()) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(SysMenu::getAuthority);

            List<String> authortityList = this.listObjs(wrapper, Object::toString);
            authoritySet = new HashSet<>(authortityList);
        } else {
            authoritySet = this.baseMapper.findAuthorityByUserId(loginUser.getId());
        }

        return authoritySet;
    }

    @Override
    public boolean checkAuthorityUnique(SysMenuAuthorityDto sysMenuAuthorityDto) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysMenu::getAuthority, SysMenu::getId)
                .eq(SysMenu::getAuthority, sysMenuAuthorityDto.getAuthority());
        SysMenu sysMenu = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同权限标识同ID为标识唯一
        return Objects.isNull(sysMenu) || Objects.equals(sysMenuAuthorityDto.getId(), sysMenu.getId());
    }

}

package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.context.LoginUser;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysMenuConvert;
import com.brycehan.boot.system.entity.dto.SysMenuDto;
import com.brycehan.boot.system.entity.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.po.SysMenu;
import com.brycehan.boot.system.entity.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
public interface SysMenuService extends BaseService<SysMenu> {

    /**
     * 添加系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     */
    default void save(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        sysMenu.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysMenu);
    }

    /**
     * 更新系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     */
    default void update(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(sysMenuDto);
        this.getBaseMapper().updateById(sysMenu);
    }

    /**
     * 系统菜单分页查询
     *
     * @param sysMenuPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysMenuVo> page(SysMenuPageDto sysMenuPageDto);

    /**
     * 系统菜单导出数据
     *
     * @param sysMenuPageDto 系统菜单查询条件
     */
    void export(SysMenuPageDto sysMenuPageDto);

    /**
     * 列表查询
     *
     * @param sysMenuDto 查询参数
     * @return 菜单列表
     */
    List<SysMenuVo> list(SysMenuDto sysMenuDto);

    /**
     * 查询用户菜单列表
     *
     * @param loginUser 登录用户
     * @param type      菜单类型
     * @return 用户菜单列表
     */
    List<SysMenuVo> getMenuTreeList(LoginUser loginUser, String type);

    /**
     * 获取子菜单个数
     *
     * @param parentIds 父菜单IDs
     * @return 子菜单个数
     */
    Long getSubMenuCount(List<Long> parentIds);

    /**
     * 查询用户权限集合
     *
     * @param loginUser 登录用户
     * @return 权限集合
     */
    Set<String> findAuthority(LoginUser loginUser);

}

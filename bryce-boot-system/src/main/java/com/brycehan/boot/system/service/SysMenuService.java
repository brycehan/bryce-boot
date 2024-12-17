package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysMenuAuthorityDto;
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
    void save(SysMenuDto sysMenuDto);

    /**
     * 更新系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     */
    void update(SysMenuDto sysMenuDto);

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
     * @param type      菜单类型，空时是所有类型
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
     * 根据用户ID查询菜单权限
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    Set<String> findAuthorityByUserId(Long userId);

    /**
     * 根据角色ID查询菜单权限
     *
     * @param roleId 角色ID
     * @return 菜单权限集合
     */
    Set<String> findAuthorityByRoleId(Long roleId);

    /**
     * 校验菜单权限标识是否唯一
     *
     * @param sysMenuAuthorityDto 菜单权限标识Dto
     * @return true：唯一，false：不唯一
     */
    boolean checkAuthorityUnique(SysMenuAuthorityDto sysMenuAuthorityDto);

}

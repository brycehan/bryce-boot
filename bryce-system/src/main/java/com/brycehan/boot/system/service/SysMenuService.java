package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.dto.SysMenuDto;
import com.brycehan.boot.system.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务
 *
 * @author Bryce Han
 * @since 2022/5/15
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
     * 系统菜单分页查询信息
     *
     * @param sysMenuPageDto 系统菜单分页搜索条件
     * @return 分页信息
     */
    PageResult<SysMenuVo> page(SysMenuPageDto sysMenuPageDto);

    /**
     * 获取用户的系统菜单列表
     *
     * @param userId 用户ID
     * @return 系统菜单列表
     */
    List<SysMenu> getSysMenuListByUserId(Long userId);

    /**
     * 查询用户菜单权限
     *
     * @param loginUser 登录用户
     * @return 菜单权限集合
     */
    Set<String> findAuthority(LoginUser loginUser);

    /**
     * 查询用户菜单列表
     *
     * @param loginUser 登录用户
     * @param type 菜单类型
     * @return 用户菜单列表
     */
    List<SysMenuVo> getMenuTreeList(LoginUser loginUser, String type);

}

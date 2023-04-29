package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.system.dto.DeleteDto;
import com.brycehan.boot.system.dto.SysMenuPageDto;
import com.brycehan.boot.system.dto.SysMenuDto;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.vo.SysMenuVo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Validated
public interface SysMenuService extends IService<SysMenu> {

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
     * 删除系统菜单
     *
     * @param deleteDto 系统菜单删除Dto
     */
    void delete(DeleteDto deleteDto);

    /**
     * 系统菜单分页查询信息
     *
     * @param sysMenuPageDto 系统菜单分页搜索条件
     * @return 分页信息
     */
    PageResult<SysMenuVo> page(@NotNull SysMenuPageDto sysMenuPageDto);

    /**
     * 获取用户的系统菜单列表
     *
     * @param userId 用户ID
     * @return 系统菜单列表
     */
    List<SysMenu> getSysMenuListByUserId(String userId);

    /**
     * 根据用户ID查询菜单权限
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    Set<String> selectMenuPermissionByUserId(String userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuVo> selectMenuTreeByUserId(@NotNull String userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    // todo 后期可能不需要
    List<MenuVo> buildMenus(List<SysMenuVo> menus);
}

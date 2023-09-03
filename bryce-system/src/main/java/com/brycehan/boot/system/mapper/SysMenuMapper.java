package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单 Mapper 接口
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Mapper
public interface SysMenuMapper extends BryceBaseMapper<SysMenu> {

    /**
     * 分页查询
     *
     * @param sysMenuPageDto 系统菜单分页数据传输对象
     * @return 系统菜单列表
     */
    List<SysMenu> page(SysMenuPageDto sysMenuPageDto);

    /**
     * 根据用户ID查询菜单权限
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    Set<String> findAuthorityByUserId(Long userId);

    /**
     * 查询菜单树列表
     *
     * @param userId 用户ID
     * @param type 菜单类型
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeList(Long userId, String type);
}

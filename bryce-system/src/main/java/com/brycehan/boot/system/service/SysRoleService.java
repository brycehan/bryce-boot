package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.system.dto.SysRoleDto;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.vo.SysRoleVo;

import java.util.List;
import java.util.Set;

/**
 * 系统角色服务类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 添加系统角色表
     *
     * @param sysRoleDto 系统角色表Dto
     */
    void save(SysRoleDto sysRoleDto);

    /**
     * 更新系统角色表
     *
     * @param sysRoleDto 系统角色表Dto
     */
    void update(SysRoleDto sysRoleDto);

    /**
     * 系统角色表分页查询信息
     *
     * @param sysRolePageDto 系统角色表分页搜索条件
     * @return 分页信息
     */
    PageResult<SysRoleVo> page(SysRolePageDto sysRolePageDto);

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 角色权限集合
     */
    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户账号查询角色
     *
     * @param username 用户账号
     * @return 角色列表
     */
    List<SysRole> selectRolesByUsername(String username);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);
}

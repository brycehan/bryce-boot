package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.entity.SysRole;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 系统角色服务类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Validated
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询信息结果
     *
     * @param sysRolePageDto 搜索条件
     * @return 分页信息
     */
    PageInfo<SysRole> page(@NotNull SysRolePageDto sysRolePageDto);

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 角色权限集合
     */
    Set<String> selectRolePermissionByUserId(String userId);

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
    List<SysRole> selectRolesByUserId(String userId);
}

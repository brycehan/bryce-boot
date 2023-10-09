package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 系统角色Mapper接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleMapper extends BryceBaseMapper<SysRole> {

    /**
     * 根据用户ID，获取用户最大的数据范围
     *
     * @param userId 用户ID
     * @return 最大的数据范围权限
     */
    Integer getDataScopeByUserId(Long userId);

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
     * 根据用户ID查询用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);

}

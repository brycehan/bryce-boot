package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.po.SysUserRole;

import java.util.List;

/**
 * 系系统用户角色关系服务
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
public interface SysUserRoleService extends BaseService<SysUserRole> {

    /**
     * 保存或修改
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     */
    void saveOrUpdate(Long userId, List<Long> roleIds);

    /**
     * 分配用户多个角色
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     */
    void assignRoleSave(Long userId, List<Long> roleIds);

    /**
     * 分配角色给多个用户
     *
     * @param roleId  角色ID
     * @param userIds 用户IDs
     */
    void assignUserSave(Long roleId, List<Long> userIds);

    /**
     * 根据角色IDs，删除用户角色关系
     *
     * @param roleIds 角色IDs
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据用户IDs，删除用户角色关系
     *
     * @param userIds 用户IDs
     */
    void deleteByUserIds(List<Long> userIds);

    /**
     * 根据角色ID和用户IDs，删除用户角色关系
     *
     * @param roleId  角色ID
     * @param userIds 用户IDs
     */
    void deleteByRoleIdAndUserIds(Long roleId, List<Long> userIds);

    /**
     * 根据用户ID和角色IDs，删除用户角色关系
     *
     * @param userId  用户ID
     * @param roleIds 角色IDs
     */
    void deleteByUserIdAndRoleIds(Long userId, List<Long> roleIds);

    /**
     * 根据用户ID，查询该用户拥有的角色IDs
     *
     * @param userId 用户ID
     * @return 拥有的角色IDs
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 根据角色ID，查询拥有该角色的用户IDs
     *
     * @param roleId 角色ID
     * @return 用户IDs
     */
    List<Long> getUserIdsByRoleId(Long roleId);

    /**
     * 根据角色ID，查询拥有该角色的用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    int countUserRoleByRoleId(Long roleId);

}

package com.brycehan.boot.api.system;

import java.util.Collection;
import java.util.List;

/**
 * Bpm 角色 API
 *
 * @since 2025/3/28
 * @author Bryce Han
 */
public interface BpmRoleApi {

    /**
     * 校验角色们是否有效。如下情况，视为无效：
     * 1. 角色编号不存在
     * 2. 角色被禁用
     *
     * @param ids 角色编号数组
     */
    void validateRoleList(Collection<Long> ids);

    /**
     * 根据角色IDs，查询拥有该角色的用户IDs
     *
     * @param roleIds 角色IDs
     * @return 用户IDs
     */
    List<Long> getUserIdsByRoleIds(List<Long> roleIds);
}

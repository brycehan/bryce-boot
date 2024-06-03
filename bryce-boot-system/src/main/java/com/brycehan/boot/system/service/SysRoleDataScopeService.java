package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.po.SysRoleDataScope;

import java.util.List;

/**
 * 系统角色数据范围服务
 *
 * @since 2023/09/15
 * @author Bryce Han
 */
public interface SysRoleDataScopeService extends BaseService<SysRoleDataScope> {

    /**
     * 保存或修改
     *
     * @param roleId 角色ID
     * @param orgIds 机构IDs
     */
    void saveOrUpdate(Long roleId, List<Long> orgIds);

    /**
     * 查询角色对应的机构IDs
     *
     * @param roleId 角色ID
     * @return 角色拥有的机构IDs
     */
    List<Long> getOrgIdsByRoleId(Long roleId);

    /**
     * 根据角色IDs，删除角色数据权限关系
     *
     * @param roleIds 角色IDs
     */
    void deleteByRoleIds(List<Long> roleIds);

}

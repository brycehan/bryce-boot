package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.SysUserRole;

import java.util.List;

/**
 * 系系统用户角色关系服务
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
public interface SysUserRoleService extends BaseService<SysUserRole> {

    /**
     * 保存或修改
     * @param userId 用户ID
     * @param roleIds 角色IDs
     */
    void saveOrUpdate(Long userId, List<Long> roleIds);


    /**
     * 查询用户的角色IDs
     *
     * @param userId 用户ID
     * @return 拥有的角色IDs
     */
    List<Long> getRoleIdsByUserId(Long userId);

}

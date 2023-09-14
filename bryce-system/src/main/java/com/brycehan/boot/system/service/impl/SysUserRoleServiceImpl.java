package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.SysUserRole;
import com.brycehan.boot.system.mapper.SysUserRoleMapper;
import com.brycehan.boot.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 系统用户角色关系服务实现
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIds) {
        // 数据库用户角色IDs
        List<Long> dbRoleIds = getRoleIdsByUserId(userId);

        // 需要新增的角色ID
        Collection<Long> insertRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        if (CollUtil.isNotEmpty(insertRoleIds)) {
            List<SysUserRole> list = insertRoleIds.stream().map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setId(IdGenerator.nextId());
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                return userRole;
            }).toList();

            this.saveBatch(list);
        }

        // 需要删除的角色ID
        Collection<Long> deleteRoleIds = CollUtil.subtract(dbRoleIds, roleIds);
        if (CollUtil.isNotEmpty(deleteRoleIds)) {
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId, userId);
            queryWrapper.in(SysUserRole::getRoleId, deleteRoleIds);

            this.remove(queryWrapper);
        }
    }

    /**
     * 根据用户ID查询拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色IDs
     */
    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);

        List<SysUserRole> sysUserRoles = this.baseMapper.selectList(queryWrapper);

        return sysUserRoles.stream().map(SysUserRole::getRoleId)
                .toList();
    }
}

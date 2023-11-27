package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.SysRoleDataScope;
import com.brycehan.boot.system.mapper.SysRoleDataScopeMapper;
import com.brycehan.boot.system.service.SysRoleDataScopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 系统角色数据范围服务实现
 *
 * @since 2023/09/15
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysRoleDataScopeServiceImpl extends BaseServiceImpl<SysRoleDataScopeMapper, SysRoleDataScope> implements SysRoleDataScopeService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long roleId, List<Long> orgIds) {
        // 数据库角色对应机构IDs
        List<Long> dbOrgIds = getOrgIdsByRoleId(roleId);

        // 需要新增的机构IDs
        Collection<Long> insertOrgIds = CollUtil.subtract(orgIds, dbOrgIds);
        if (CollUtil.isNotEmpty(insertOrgIds)) {
            List<SysRoleDataScope> list = insertOrgIds.stream().map(orgId -> {
                SysRoleDataScope dataScope = new SysRoleDataScope();
                dataScope.setId(IdGenerator.nextId());
                dataScope.setOrgId(orgId);
                dataScope.setRoleId(roleId);
                return dataScope;
            }).toList();

            // 批量新增
            this.saveBatch(list);
        }

        // 需要删除的机构IDs
        Collection<Long> deleteOrgIds = CollUtil.subtract(dbOrgIds, orgIds);
        if (CollUtil.isNotEmpty(deleteOrgIds)) {
            LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleDataScope::getRoleId, roleId);
            queryWrapper.in(SysRoleDataScope::getOrgId, deleteOrgIds);

            this.remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getOrgIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleDataScope::getRoleId, roleId);

        List<SysRoleDataScope> sysUserRoles = this.baseMapper.selectList(queryWrapper);

        return sysUserRoles.stream().map(SysRoleDataScope::getOrgId)
                .toList();
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        this.baseMapper.delete(new LambdaQueryWrapper<SysRoleDataScope>().in(SysRoleDataScope::getRoleId, roleIds));
    }

}

package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.po.SysRoleOrg;
import com.brycehan.boot.system.mapper.SysRoleOrgMapper;
import com.brycehan.boot.system.service.SysRoleOrgService;
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
public class SysRoleOrgServiceImpl extends BaseServiceImpl<SysRoleOrgMapper, SysRoleOrg> implements SysRoleOrgService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long roleId, List<Long> deptIds) {
        // 数据库角色对应部门IDs
        List<Long> dbDeptIds = getDeptIdsByRoleId(roleId);

        // 需要新增的部门IDs
        Collection<Long> insertDeptIds = CollUtil.subtract(deptIds, dbDeptIds);
        if (CollUtil.isNotEmpty(insertDeptIds)) {
            List<SysRoleOrg> list = insertDeptIds.stream().map(deptId -> {
                SysRoleOrg dataScope = new SysRoleOrg();
                dataScope.setId(IdGenerator.nextId());
                dataScope.setDeptId(deptId);
                dataScope.setRoleId(roleId);
                return dataScope;
            }).toList();

            // 批量新增
            saveBatch(list);
        }

        // 需要删除的部门IDs
        Collection<Long> deleteDeptIds = CollUtil.subtract(dbDeptIds, deptIds);
        if (CollUtil.isNotEmpty(deleteDeptIds)) {
            LambdaQueryWrapper<SysRoleOrg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleOrg::getRoleId, roleId);
            queryWrapper.in(SysRoleOrg::getDeptId, deleteDeptIds);

            remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getDeptIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleOrg::getRoleId, roleId);

        List<SysRoleOrg> sysUserRoles = baseMapper.selectList(queryWrapper);

        return sysUserRoles.stream().map(SysRoleOrg::getDeptId)
                .toList();
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        baseMapper.delete(new LambdaQueryWrapper<SysRoleOrg>().in(SysRoleOrg::getRoleId, roleIds));
    }

}

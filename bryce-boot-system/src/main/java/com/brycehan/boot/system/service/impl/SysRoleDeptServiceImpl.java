package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.po.SysRoleDept;
import com.brycehan.boot.system.mapper.SysRoleDeptMapper;
import com.brycehan.boot.system.service.SysRoleDeptService;
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
public class SysRoleDeptServiceImpl extends BaseServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long roleId, List<Long> deptIds) {
        // 数据库角色对应部门IDs
        List<Long> dbDeptIds = getDeptIdsByRoleId(roleId);

        // 需要新增的部门IDs
        Collection<Long> insertDeptIds = CollUtil.subtract(deptIds, dbDeptIds);
        if (CollUtil.isNotEmpty(insertDeptIds)) {
            List<SysRoleDept> list = insertDeptIds.stream().map(deptId -> {
                SysRoleDept dataScope = new SysRoleDept();
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
            LambdaQueryWrapper<SysRoleDept> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleDept::getRoleId, roleId);
            queryWrapper.in(SysRoleDept::getDeptId, deleteDeptIds);

            remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getDeptIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleDept::getRoleId, roleId);

        List<SysRoleDept> sysUserRoles = baseMapper.selectList(queryWrapper);

        return sysUserRoles.stream().map(SysRoleDept::getDeptId)
                .toList();
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        baseMapper.delete(new LambdaQueryWrapper<SysRoleDept>().in(SysRoleDept::getRoleId, roleIds));
    }

}

package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.system.mapper.SysUserRoleMapper;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.entity.SysUserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统用户角色中间表服务实现类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    public SysUserRoleServiceImpl(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    /**
     * 根据用户ID查询拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Override
    public List<Long> getRoleIdListByUserId(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), "user_id", userId);
        List<SysUserRole> sysUserRoles = this.sysUserRoleMapper.selectList(queryWrapper);
        return sysUserRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
    }
}

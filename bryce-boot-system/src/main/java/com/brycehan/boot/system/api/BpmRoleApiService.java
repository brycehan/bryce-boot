package com.brycehan.boot.system.api;

import com.brycehan.boot.api.system.BpmRoleApi;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Bpm 角色 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmRoleApiService implements BpmRoleApi {

    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;

    @Override
    public void validateRoleList(Collection<Long> roleIds) {
        sysRoleService.validateRoleList(roleIds);
    }

    @Override
    public List<Long> getUserIdsByRoleIds(List<Long> roleIds) {
        return sysUserRoleService.getUserIdsByRoleIds(roleIds);
    }
}

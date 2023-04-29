package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.entity.SysUserRole;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 系统用户角色中间表服务类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Validated
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户ID查询拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 拥有的角色ID列表
     */
    List<String> getRoleIdListByUserId(String userId);
}

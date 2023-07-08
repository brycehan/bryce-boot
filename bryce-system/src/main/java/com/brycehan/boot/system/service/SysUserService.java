package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.dto.SysUserPageDto;
import com.brycehan.boot.system.entity.SysUser;
import com.github.pagehelper.PageInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author Bryce Han
 * @since 2022/5/08
 */
@Validated
public interface SysUserService extends IService<SysUser> {


    /**
     * 注册用户
     *
     * @param sysUser 用户
     */
    void registerUser(SysUser sysUser);

    /**
     * 分页查询信息结果
     *
     * @param sysUserPageDto 搜索条件
     * @return 分页信息
     */
    PageInfo<SysUser> page(@NotNull SysUserPageDto sysUserPageDto);

    /**
     * 校验用户账号是否唯一
     *
     * @param sysUser 用户
     * @return 结果，true唯一
     */
    boolean checkUsernameUnique(SysUser sysUser);

    /**
     * 校验用户手机号是否唯一
     *
     * @param sysUser 用户
     * @return 结果，true唯一
     */
    boolean checkPhoneUnique(SysUser sysUser);

    /**
     * 校验用户邮箱是否唯一
     *
     * @param sysUser 用户
     * @return 结果，true唯一
     */
    boolean checkEmailUnique(SysUser sysUser);

    /**
     * 校验用户是否允许操作（admin放行，普通用户不能操作admin账号）
     *
     * @param sysUser 系统用户信息
     */
    void checkUserAllowed(SysUser sysUser);

    /**
     * 根据用户账号查询用户所属角色组
     *
     * @param username 用户账号
     * @return 所属角色组（多个时逗号分隔）
     */
    String selectUserRoleGroup(String username);

    /**
     * 根据用户账号查询用户所属岗位组
     *
     * @param username 用户账号
     * @return 所属岗位组（多个时逗号分隔）
     */
    String selectUserPostGroup(String username);

    /**
     * 修改用户头像
     *
     * @param userId 用户ID
     * @param avatar 头像地址
     * @return 修改结果
     */
    boolean updateUserAvatar(String userId, String avatar);

    void insertAuthRole(String userId, Long[] roleIds);

}

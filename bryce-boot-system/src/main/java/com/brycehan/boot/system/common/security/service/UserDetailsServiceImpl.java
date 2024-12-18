package com.brycehan.boot.system.common.security.service;

import com.brycehan.boot.api.system.SysUserApi;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.SysUserDetailsService;
import com.brycehan.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务
 *
 * @author Bryce Han
 * @since 2022/5/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, SysUserApi {

    private final SysUserService sysUserService;

    private final SysUserDetailsService sysUserDetailsService;

    /**
     * 获取用户登录信息
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails实例
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        SysUser sysUser = sysUserService.getByUsername(username);

        if (sysUser == null) {
            log.debug("loadUserByUsername, 登录用户：{}不存在.", username);
            throw new UsernameNotFoundException("账号或密码错误");
        }

        // 创建用户详情
        return this.sysUserDetailsService.getUserDetails(sysUser);
    }

    /**
     * 获取登录用户
     *
     * @param id 用户ID
     * @return 登录用户
     */
    @Override
    public LoginUser loadUserById(Long id) {
        // 查询用户
        SysUser sysUser = sysUserService.getById(id);

        if (sysUser == null) {
            log.debug("loadUserById, 登录用户：{}不存在.", id);
            throw new UsernameNotFoundException("账号或密码错误");
        }

        // 创建用户详情
        return SysUserConvert.INSTANCE.convertLoginUser(sysUser);
    }
}

package com.brycehan.boot.system.security;

import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.convert.SysUserConvert;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * 用户详情服务
 *
 * @since 2022/5/6
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    private final SysPasswordService sysPasswordService;

    private final SysMenuService sysMenuService;

    /**
     * 获取用户登录信息
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails实例
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1、查询用户
        SysUser sysUser = sysUserMapper.getByUsername(username);

        if (Objects.isNull(sysUser)) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}不存在.", username);
            throw BusinessException.responseStatus(UserResponseStatus.USER_USERNAME_OR_PASSWORD_ERROR);
        }

        // 4、创建用户详情
        return createLoginUser(sysUser);
    }

    /**
     * 创建用户详情【用户登录信息】
     *
     * @param sysUser 系统用户
     * @return UserDetails实例
     */
    private UserDetails createLoginUser(SysUser sysUser) {
        LoginUser loginUser = SysUserConvert.INSTANCE.convertLoginUser(sysUser);

        // 账号不可用
        if (!sysUser.getStatus()) {
            loginUser.setEnabled(false);
        } else if (!sysUser.getAccountNonLocked()) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被锁定.", loginUser.getUsername());
            throw BusinessException.responseStatus(UserResponseStatus.USER_ACCOUNT_LOCKED);
        } else if (sysUser.getDeleted()) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被删除.", loginUser.getUsername());
            throw BusinessException.responseStatus(UserResponseStatus.USER_ACCOUNT_DELETED);
        }

        // 2、处理密码错误重试次数
        this.sysPasswordService.validate(sysUser);

        // 用户权限集合
        Set<String> authoritySet = this.sysMenuService.findAuthority(loginUser);
        loginUser.setAuthoritySet(authoritySet);

        return loginUser;
    }

}

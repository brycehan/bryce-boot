package com.brycehan.boot.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.system.context.LoginUser;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysPasswordService;
import com.brycehan.boot.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户详情服务
 *
 * @author Bryce Han
 * @since 2022/5/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    private final SysPasswordService sysPasswordService;

    private final SysPermissionService sysPermissionService;

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
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);

        // 2、增强处理
        if (Objects.isNull(sysUser)) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}不存在.", username);
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_USERNAME_OR_PASSWORD_ERROR);
        } else if (sysUser.getStatus() == 0) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被停用.", username);
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_ACCOUNT_DISABLED);
            // todo 测试下面3行代码不需要
        } else if (!sysUser.getAccountNonLocked()) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被锁定.", username);
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_ACCOUNT_LOCKED);
        } else if (sysUser.getDeleteFlag()) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被删除.", username);
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_ACCOUNT_DELETED);
        }

        // 3、处理密码错误重试次数
        this.sysPasswordService.validate(sysUser);
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
        // 1、查询用户的权限
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<String> rolePermission = this.sysPermissionService.getRolePermission(sysUser);
        sysUser.setRoles(rolePermission);
        Set<String> menuPermission = this.sysPermissionService.getMenuPermission(sysUser);
        sysUser.setPermissions(menuPermission);
        // 2、封装用户的权限数据
        if (Objects.nonNull(rolePermission)) {
            List<SimpleGrantedAuthority> roleAuthorities = rolePermission.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role))).toList();
            grantedAuthorities.addAll(roleAuthorities);
        }
        if (Objects.nonNull(menuPermission)) {
            List<SimpleGrantedAuthority> menuAuthorities = menuPermission.stream()
                    .filter(StringUtils::isNotEmpty)
                    .map(SimpleGrantedAuthority::new).toList();
            grantedAuthorities.addAll(menuAuthorities);
        }
        // 3、封装用户详情实例
        return new LoginUser(sysUser, grantedAuthorities);
    }

}

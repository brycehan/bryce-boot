package com.brycehan.boot.system.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.convert.SysUserConvert;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysPasswordService;
import com.brycehan.boot.system.service.SysAuthorityService;
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

    private final SysAuthorityService sysAuthorityService;

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

        if (Objects.isNull(sysUser)) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}不存在.", username);
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_USERNAME_OR_PASSWORD_ERROR);
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
        if (sysUser.getStatus() == 0) {
//            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被停用.", loginUser.getUsername());
//            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_ACCOUNT_DISABLED);
            loginUser.setEnabled(false);
        } else if (!sysUser.getAccountNonLocked()) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被锁定.", loginUser.getUsername());
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_ACCOUNT_LOCKED);
        } else if (sysUser.getDeleted()) {
            log.info("UserDetailsServiceImpl.loadUserByUsername, 登录用户：{}已被删除.", loginUser.getUsername());
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_ACCOUNT_DELETED);
        }

        // 2、处理密码错误重试次数
        this.sysPasswordService.validate(sysUser);

        // 用户权限列表
        Set<String> authoritySet = this.sysAuthorityService.getUserAuthority(loginUser);
        loginUser.setAuthoritySet(authoritySet);

        // 1、查询用户的权限
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<String> rolePermission = this.sysAuthorityService.getRoleAuthority(loginUser);
        sysUser.setRoles(rolePermission);
        Set<String> menuAuthoritySet = this.sysAuthorityService.getMenuAuthority(loginUser);
        sysUser.setAuthoritySet(menuAuthoritySet);
        // 2、封装用户的权限数据
        if (Objects.nonNull(rolePermission)) {
            List<SimpleGrantedAuthority> roleAuthorities = rolePermission.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role))).toList();
            grantedAuthorities.addAll(roleAuthorities);
        }
        if (Objects.nonNull(menuAuthoritySet)) {
            List<SimpleGrantedAuthority> menuAuthorities = menuAuthoritySet.stream()
                    .filter(StringUtils::isNotEmpty)
                    .map(SimpleGrantedAuthority::new).toList();
            grantedAuthorities.addAll(menuAuthorities);
        }

        return loginUser;
    }

}

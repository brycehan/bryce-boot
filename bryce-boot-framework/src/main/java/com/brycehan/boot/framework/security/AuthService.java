package com.brycehan.boot.framework.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.constant.DataConstants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

/**
 * 权限服务
 *
 * @author Bryce Han
 * @since 2024/12/12
 */
@SuppressWarnings("unused")
@Service("auth")
public class AuthService {

    /**
     * 判断用户是否拥有某个权限
     *
     * @param authority 权限
     * @return true/false
     */
    public boolean hasAuthority(String authority) {
        if (StrUtil.isBlank(authority)) {
            return false;
        }

        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser == null || CollUtil.isEmpty(loginUser.getAuthoritySet())) {
            return false;
        }

        return hasAnyAuthority(loginUser.getAuthoritySet(), authority);
    }

    /**
     * 判断用户是否拥有任意一个权限
     *
     * @param authorities 权限
     * @return true/false
     */
    public boolean hasAnyAuthority(String... authorities) {
        if (ArrayUtil.isEmpty(authorities)) {
            return false;
        }

        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser == null || CollUtil.isEmpty(loginUser.getAuthoritySet())) {
            return false;
        }

        return hasAnyAuthority(loginUser.getAuthoritySet(), authorities);
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色
     * @return true/false
     */
    public boolean hasRole(String role) {
        if (StrUtil.isBlank(role)) {
            return false;
        }

        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser == null || CollUtil.isEmpty(loginUser.getRoleSet())) {
            return false;
        }

        return hasAnyAuthority(loginUser.getRoleSet(), role);
    }

    /**
     * 判断用户是否拥有任意一个角色
     *
     * @param role 角色
     * @return true/false
     */
    public boolean hasAnyRole(String... role) {
        if (ArrayUtil.isEmpty(role)) {
            return false;
        }

        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser == null || CollUtil.isEmpty(loginUser.getRoleSet())) {
            return false;
        }

        return hasAnyAuthority(loginUser.getRoleSet(), role);
    }

    /**
     * 判断用户是否拥有任意一个权限
     *
     * @param authoritySet 权限集合
     * @param authorities 权限
     * @return true/false
     */
    private boolean hasAnyAuthority(Set<String> authoritySet, String... authorities) {
        return authoritySet.contains(DataConstants.ROLE_SUPER_ADMIN) || Arrays.stream(authorities).anyMatch(authoritySet::contains);
    }

}

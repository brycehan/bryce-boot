package com.brycehan.boot.common.base;

import com.brycehan.boot.common.entity.vo.RoleVo;
import com.brycehan.boot.common.enums.GenderType;
import com.brycehan.boot.common.enums.SourceClientType;
import com.brycehan.boot.common.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户身份权限
 *
 * @since 2022/5/6
 * @author Bryce Han
 */
@Data
public class LoginUser implements UserDetails {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String nickname;

    private String avatar;

    private GenderType gender;

    private String email;

    private String phone;

    private Long orgId;

    private String orgName;

    private StatusType status;

    /**
     * 用户登录存储Key
     */
    private String userKey;

    /**
     * 记住我
     */
    private Boolean rememberMe;

    /**
     * 来源客户端
     */
    private SourceClientType sourceClientType;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 浏览器信息
     */
    private String userAgent;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录位置
     */
    private String loginLocation;

    /**
     * 小程序openId
     */
    private String openId;

    /**
     * 帐户是否未过期
     */
    private boolean accountNonExpired = true;

    /**
     * 账户是否处于锁定或解锁状态
     */
    private boolean accountNonLocked = true;

    /**
     * 凭据(密码)是否已过期
     */
    private boolean credentialsNonExpired = true;

    /**
     * 账户是启用还是禁用
     */
    private boolean enabled = true;

    /**
     * 用户所在机构的下级机构集合（包含用户的所在机构）
     */
    private Set<Long> SubOrgIds;

    /**
     * 拥有权限集合
     */
    private Set<String> authoritySet;

    /**
     * 拥有角色集合
     */
    private Set<RoleVo> roles;

    /**
     * 拥有角色编码集合
     */
    private Set<String> roleSet;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet.stream().filter(StringUtils::isNotBlank)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 是否是超级管理员
     *
     * @return true 是超级管理员
     */
    @JsonIgnore
    public boolean isSuperAdmin() {
        return LoginUser.isSuperAdmin(this);
    }

    /**
     * 是否是超级管理员
     *
     * @param loginUser 登录用户
     * @return true 是超级管理员
     */
    public static boolean isSuperAdmin(LoginUser loginUser) {
        return Optional.ofNullable(loginUser).map(LoginUser::getId).map(id -> id == 1L).orElse(false);
    }
}

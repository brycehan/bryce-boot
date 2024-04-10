package com.brycehan.boot.framework.security.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
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

    private String fullName;

    private String avatar;

    private String gender;

    private String email;

    private String phone;

    private Long orgId;

    private Boolean status;

    private Boolean superAdmin;

    /**
     * 用户令牌存储Key
     */
    private String tokenKey;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 来源客户端（pc、app、miniApp）
     */
    private String sourceClient;

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
     * 数据权限范围集合
     * <p>null：表示全部数据权限</p>
     */
    private Set<Long> dataScopeSet;

    /**
     * 拥有权限集合
     */
    private Set<String> authoritySet;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet.stream().filter(StringUtils::isNotBlank)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}

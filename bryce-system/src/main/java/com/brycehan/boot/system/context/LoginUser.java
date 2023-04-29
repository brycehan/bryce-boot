package com.brycehan.boot.system.context;

import com.brycehan.boot.system.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * 登录用户身份权限
 *
 * @author Bryce Han
 * @since 2022/5/6
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// TODO PASSWORD 序列化设置为false
public class LoginUser implements UserDetails {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号没有锁定
     */
    private Boolean accountNonLocked;

    /**
     * 用户令牌
     */
    private String token;

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
    private String ipAddress;

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
     * 登录的系统用户信息
     */
    private SysUser sysUser;

    /**
     * 权限集合
     */
    private Collection<? extends GrantedAuthority> authorities;

    public LoginUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        this.sysUser = sysUser;
        if (Objects.nonNull(sysUser)) {
            this.id = sysUser.getId();
            this.username = sysUser.getUsername();
            this.password = sysUser.getPassword();
            this.accountNonLocked = sysUser.getAccountNonLocked();
        }
        this.authorities = authorities;
    }

    /**
     * 指示用户的帐户是否未过期。过期的帐号无法鉴权。
     *
     * @return 帐户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指示用户是否处于锁定或解锁状态。用户被锁定后，无法进行认证。
     *
     * @return 用户是否处于解锁状态
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * 指示用户的凭据(密码)是否已过期。过期的凭据将阻止身份验证。
     *
     * @return 用户的凭据(密码)是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 指示用户是启用还是禁用。禁用的用户无法进行身份验证。
     *
     * @return 用户是否启用的状态
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

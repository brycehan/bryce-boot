package com.brycehan.boot.framework.security.phone;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serial;
import java.util.Collection;

/**
 * 手机短信登录，认证令牌
 *
 * @since 2023/10/7
 * @author Bryce Han
 */
public class PhoneCodeAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private String code;

    public PhoneCodeAuthenticationToken(Object principal, String code) {
        super(null);
        this.principal = principal;
        this.code = code;
        setAuthenticated(false);
    }

    public PhoneCodeAuthenticationToken(Object principal, String code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.code = code;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.code;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.code = null;
    }
}

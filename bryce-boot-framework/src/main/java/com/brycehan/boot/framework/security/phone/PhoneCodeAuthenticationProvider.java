package com.brycehan.boot.framework.security.phone;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * @since 2023/10/7
 * @author Bryce Han
 */
public class PhoneCodeAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private final PhoneCodeUserDetailsService phoneCodeUserDetailsService;

    private final PhoneCodeValidateService phoneCodeValidateService;

    public PhoneCodeAuthenticationProvider(PhoneCodeUserDetailsService phoneCodeUserDetailsService, PhoneCodeValidateService phoneCodeValidateService) {
        this.phoneCodeUserDetailsService = phoneCodeUserDetailsService;
        this.phoneCodeValidateService = phoneCodeValidateService;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.phoneCodeUserDetailsService, "phoneCodeUserDetailsService must not be null");
        Assert.notNull(this.phoneCodeValidateService, "phoneCodeValidateService must not be null");
    }

    @Override
    public void setMessageSource(@NotNull MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(PhoneCodeAuthenticationToken.class, authentication,
                () -> messages.getMessage("PhoneCodeAuthenticationProvider.onlySupports",
                        "Only PhoneCodeAuthenticationProvider is supported"));
        PhoneCodeAuthenticationToken authenticationToken = (PhoneCodeAuthenticationToken) authentication;
        String phone = authenticationToken.getName();
        String code = (String) authenticationToken.getCredentials();

        try {
            UserDetails userDetails = this.phoneCodeUserDetailsService.loadUserByPhone(phone);
            if(userDetails == null) {
                throw new BadCredentialsException("Bad credentials");
            }

            // 短信验证码校验
            if(this.phoneCodeValidateService.validate(phone, code)) {
                return createSuccessAuthentication(authentication, userDetails);
            } else {
                throw new BadCredentialsException("手机验证码错误");
            }
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException(this.messages
                    .getMessage("PhoneCodeAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    private Authentication createSuccessAuthentication(Authentication authentication, UserDetails userDetails) {
        PhoneCodeAuthenticationToken authenticationToken = new PhoneCodeAuthenticationToken(userDetails, null,
                this.authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

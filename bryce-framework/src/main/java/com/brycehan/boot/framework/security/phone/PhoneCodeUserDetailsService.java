package com.brycehan.boot.framework.security.phone;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 手机短信登录，UserDetailsService
 *
 * @since 2023/10/7
 * @author Bryce Han
 */
public interface PhoneCodeUserDetailsService {

    /**
     * 通过手机号加载用户信息
     *
     * @param phone 手机号
     * @return 用户信息
     */
    UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;

}

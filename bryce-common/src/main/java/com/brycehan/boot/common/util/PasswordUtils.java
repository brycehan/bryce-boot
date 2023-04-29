package com.brycehan.boot.common.util;

import com.brycehan.boot.common.base.context.SpringContextHolder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 字符串工具类
 *
 * @author Bryce Han
 * @since 2022/5/16
 */
public class PasswordUtils {

    private static PasswordEncoder passwordEncoder = SpringContextHolder.getBean(PasswordEncoder.class);

    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {

        System.out.println("appId：" + RandomStringUtils.randomAlphanumeric(20));
        String p = new BCryptPasswordEncoder().encode("123456");

        System.out.println("password：".concat(p));
        System.out.println("password2：" + new BCryptPasswordEncoder().matches("123456", p));


    }

}

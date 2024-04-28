package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.dto.AccountLoginDto;
import com.brycehan.boot.common.base.dto.PhoneLoginDto;
import com.brycehan.boot.common.base.vo.LoginVo;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 认证服务
 *
 * @since 2022/9/16
 * @author Bryce Han
 */

public interface AuthService {

    /**
     * 账号登录
     *
     * @param accountLoginDto 账号登录dto
     * @return 登录 Vo
     */
    LoginVo loginByAccount(AccountLoginDto accountLoginDto);

    /**
     * 手机号登录
     *
     * @param phoneLoginDto 手机号登录dto
     * @return 登录 Vo
     */
    LoginVo loginByPhone(PhoneLoginDto phoneLoginDto);

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户
     */
    void updateLoginInfo(UserDetails loginUser);

    /**
     * 退出登录
     *
     * @param accessToken 访问令牌
     */
    void logout(String accessToken);

    /**
     * APP账号登录
     *
     * @param accountLoginDto 账号登录dto
     * @return 登录 Vo
     */
    LoginVo appLoginByAccount(AccountLoginDto accountLoginDto);

}

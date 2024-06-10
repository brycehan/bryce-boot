package com.brycehan.boot.ma.service.impl;

import com.brycehan.boot.api.ma.MaUserApi;
import com.brycehan.boot.api.ma.vo.MaUserVo;
import com.brycehan.boot.ma.entity.po.MaUser;
import com.brycehan.boot.ma.service.MaUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * 微信小程序用户服务实现
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MaUserApiImpl implements MaUserApi {

    private final MaUserService maUserService;

    @Override
    public MaUserVo loadMaUserByOpenid(String openid) {
        MaUser maUser = this.maUserService.getByOpenId(openid);
        if (maUser != null) {
            MaUserVo maUserVo = new MaUserVo();
            BeanUtils.copyProperties(maUser, maUserVo);
            return maUserVo;
        }
        return null;
    }

}

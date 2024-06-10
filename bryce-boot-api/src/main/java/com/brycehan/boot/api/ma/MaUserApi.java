package com.brycehan.boot.api.ma;

import com.brycehan.boot.api.ma.vo.MaUserVo;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微信小程序Api
 *
 * @since 2024/4/7
 * @author Bryce Han
 */
public interface MaUserApi {

    /**
     * 获取参数对象
     *
     * @param openid 参数key
     * @return 参数对象
     */
    MaUserVo loadMaUserByOpenid(@RequestParam String openid);

}

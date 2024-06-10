package com.brycehan.boot.ma.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.ma.entity.dto.MaLoginDto;
import com.brycehan.boot.ma.entity.dto.MaUserDto;
import com.brycehan.boot.ma.entity.dto.MaUserPageDto;
import com.brycehan.boot.ma.entity.po.MaUser;
import com.brycehan.boot.ma.entity.vo.MaUserLoginVo;
import com.brycehan.boot.ma.entity.vo.MaUserVo;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信小程序用户服务
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
public interface MaUserService extends BaseService<MaUser> {

    /**
     * 更新微信小程序用户
     *
     * @param maUserDto 微信小程序用户Dto
     */
    MaUserVo update(MaUserDto maUserDto);

    /**
     * 微信小程序用户分页查询
     *
     * @param maUserPageDto 查询条件
     * @return 分页信息
     */
    PageResult<MaUserVo> page(MaUserPageDto maUserPageDto);

    /**
     * 微信小程序用户导出数据
     *
     * @param maUserPageDto 微信小程序用户查询条件
     */
    void export(MaUserPageDto maUserPageDto);

    /**
     * 根据openId查询微信小程序用户
     *
     * @param openId openId
     * @return 微信小程序用户
     */
    MaUser getByOpenId(String openId);

    /**
     * 手机号快捷登录
     *
     * @param maLoginDto 手机号登录参数
     * @return 用户信息
     */
    MaUserLoginVo login(MaLoginDto maLoginDto) throws WxErrorException;

}

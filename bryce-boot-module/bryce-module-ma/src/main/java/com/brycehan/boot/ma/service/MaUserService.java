package com.brycehan.boot.ma.service;

import com.brycehan.boot.api.ma.MaUserApi;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.ma.convert.MaUserConvert;
import com.brycehan.boot.ma.dto.MaLoginDto;
import com.brycehan.boot.ma.dto.MaUserDto;
import com.brycehan.boot.ma.dto.MaUserPageDto;
import com.brycehan.boot.ma.entity.MaUser;
import com.brycehan.boot.ma.vo.MaUserLoginVo;
import com.brycehan.boot.ma.vo.MaUserVo;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信小程序用户服务
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
public interface MaUserService extends BaseService<MaUser>, MaUserApi {

    /**
     * 添加微信小程序用户
     *
     * @param maUserDto 微信小程序用户Dto
     */
    default void save(MaUserDto maUserDto) {
        MaUser maUser = MaUserConvert.INSTANCE.convert(maUserDto);
        maUser.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(maUser);
    }

    /**
     * 更新微信小程序用户
     *
     * @param maUserDto 微信小程序用户Dto
     */
    default void update(MaUserDto maUserDto) {
        MaUser maUser = MaUserConvert.INSTANCE.convert(maUserDto);
        this.getBaseMapper().updateById(maUser);
    }

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

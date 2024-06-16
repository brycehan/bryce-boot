package com.brycehan.boot.mp.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.mp.entity.convert.MpUserConvert;
import com.brycehan.boot.mp.entity.dto.MpUserDto;
import com.brycehan.boot.mp.entity.dto.MpUserPageDto;
import com.brycehan.boot.mp.entity.po.MpUser;
import com.brycehan.boot.mp.entity.vo.MpUserVo;

import java.util.List;

/**
 * 微信公众号粉丝服务
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
public interface MpUserService extends BaseService<MpUser> {

    /**
     * 添加微信公众号粉丝
     *
     * @param mpUserDto 微信公众号粉丝Dto
     */
    default void save(MpUserDto mpUserDto) {
        MpUser mpUser = MpUserConvert.INSTANCE.convert(mpUserDto);
        this.getBaseMapper().insert(mpUser);
    }

    /**
     * 更新微信公众号粉丝
     *
     * @param mpUserDto 微信公众号粉丝Dto
     */
    default void update(MpUserDto mpUserDto) {
        MpUser mpUser = MpUserConvert.INSTANCE.convert(mpUserDto);
        this.getBaseMapper().updateById(mpUser);
    }

    /**
     * 微信公众号粉丝分页查询
     *
     * @param mpUserPageDto 查询条件
     * @return 分页信息
     */
    PageResult<MpUserVo> page(MpUserPageDto mpUserPageDto);

    /**
     * 微信公众号粉丝导出数据
     *
     * @param mpUserPageDto 微信公众号粉丝查询条件
     */
    void export(MpUserPageDto mpUserPageDto);

    /**
     * 根据openid更新用户信息
     *
     * @param openid openid
     */
    MpUser refreshUserInfo(String openid);

    /**
     * 异步批量根据openid更新用户信息
     *
     * @param openidList openid列表
     */
    void refreshUserInfoAsync(String[] openidList);

    /**
     * 取消关注，更新关注状态
     *
     * @param openid openid
     */
    void unsubscribe(String openid);

    /**
     * 同步用户列表<br>
     * 公众号一次拉取调用最多拉取10000个关注者的openid，可以通过传入nextOpenid参数多次拉取
     */
    void syncMpUsers();

    /**
     * 通过传入的openid列表，同步用户列表
     *
     * @param openidList openid列表
     */
    void syncMpUsers(List<String> openidList);

    /**
     * 通过openid获取用户信息
     *
     * @param openid openid
     * @return 用户信息
     */
    MpUser getByOpenid(String openid);

}

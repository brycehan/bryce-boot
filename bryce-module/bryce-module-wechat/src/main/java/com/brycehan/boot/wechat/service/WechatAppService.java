package com.brycehan.boot.wechat.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.wechat.convert.WechatAppConvert;
import com.brycehan.boot.wechat.dto.WechatAppDto;
import com.brycehan.boot.wechat.dto.WechatAppPageDto;
import com.brycehan.boot.wechat.entity.WechatApp;
import com.brycehan.boot.wechat.vo.WechatAppVo;

import java.util.List;

/**
 * 微信应用服务
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
public interface WechatAppService extends BaseService<WechatApp> {

    /**
     * 添加微信应用
     *
     * @param wechatAppDto 微信应用Dto
     */
    default void save(WechatAppDto wechatAppDto) {
        WechatApp wechatApp = WechatAppConvert.INSTANCE.convert(wechatAppDto);
        wechatApp.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(wechatApp);
    }

    /**
     * 更新微信应用
     *
     * @param wechatAppDto 微信应用Dto
     */
    default void update(WechatAppDto wechatAppDto) {
        WechatApp wechatApp = WechatAppConvert.INSTANCE.convert(wechatAppDto);
        this.getBaseMapper().updateById(wechatApp);
    }

    /**
     * 微信应用分页查询
     *
     * @param wechatAppPageDto 查询条件
     * @return 分页信息
     */
    PageResult<WechatAppVo> page(WechatAppPageDto wechatAppPageDto);

    /**
     * 微信应用导出数据
     *
     * @param wechatAppPageDto 微信应用查询条件
     */
    void export(WechatAppPageDto wechatAppPageDto);

    /**
     * 微信应用列表查询
     *
     * @param wechatAppDto 查询条件
     * @return 列表
     */
    List<WechatAppVo> list(WechatAppDto wechatAppDto);

}

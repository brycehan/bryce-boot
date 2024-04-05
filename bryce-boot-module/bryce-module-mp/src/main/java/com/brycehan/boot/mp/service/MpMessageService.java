package com.brycehan.boot.mp.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.mp.convert.MpMessageConvert;
import com.brycehan.boot.mp.dto.MpMessageDto;
import com.brycehan.boot.mp.dto.MpMessagePageDto;
import com.brycehan.boot.mp.entity.MpMessage;
import com.brycehan.boot.mp.vo.MpMessageVo;

/**
 * 微信公众号消息服务
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
public interface MpMessageService extends BaseService<MpMessage> {

    /**
     * 添加微信公众号消息
     *
     * @param mpMessageDto 微信公众号消息Dto
     */
    default void save(MpMessageDto mpMessageDto) {
        MpMessage mpMessage = MpMessageConvert.INSTANCE.convert(mpMessageDto);
        mpMessage.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(mpMessage);
    }

    /**
     * 更新微信公众号消息
     *
     * @param mpMessageDto 微信公众号消息Dto
     */
    default void update(MpMessageDto mpMessageDto) {
        MpMessage mpMessage = MpMessageConvert.INSTANCE.convert(mpMessageDto);
        this.getBaseMapper().updateById(mpMessage);
    }

    /**
     * 微信公众号消息分页查询
     *
     * @param mpMessagePageDto 查询条件
     * @return 分页信息
     */
    PageResult<MpMessageVo> page(MpMessagePageDto mpMessagePageDto);

    /**
     * 微信公众号消息导出数据
     *
     * @param mpMessagePageDto 微信公众号消息查询条件
     */
    void export(MpMessagePageDto mpMessagePageDto);

    /**
     * 记录消息，异步入库
     *
     * @param mpMessage 消息
     */
    void addMpMessage(MpMessage mpMessage);

}

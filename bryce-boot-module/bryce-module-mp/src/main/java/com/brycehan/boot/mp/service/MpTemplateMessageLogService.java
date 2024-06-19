package com.brycehan.boot.mp.service;

import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.mp.entity.convert.MpTemplateMessageLogConvert;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageLogDto;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageLogPageDto;
import com.brycehan.boot.mp.entity.po.MpTemplateMessageLog;
import com.brycehan.boot.mp.entity.vo.MpTemplateMessageLogVo;

/**
 * 微信公众号模版消息发送记录服务
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
public interface MpTemplateMessageLogService extends BaseService<MpTemplateMessageLog> {

    /**
     * 添加微信公众号模版消息发送记录
     *
     * @param mpTemplateMessageLogDto 微信公众号模版消息发送记录Dto
     */
    default void save(MpTemplateMessageLogDto mpTemplateMessageLogDto) {
        MpTemplateMessageLog mpTemplateMessageLog = MpTemplateMessageLogConvert.INSTANCE.convert(mpTemplateMessageLogDto);
        mpTemplateMessageLog.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(mpTemplateMessageLog);
    }

    /**
     * 更新微信公众号模版消息发送记录
     *
     * @param mpTemplateMessageLogDto 微信公众号模版消息发送记录Dto
     */
    default void update(MpTemplateMessageLogDto mpTemplateMessageLogDto) {
        MpTemplateMessageLog mpTemplateMessageLog = MpTemplateMessageLogConvert.INSTANCE.convert(mpTemplateMessageLogDto);
        this.getBaseMapper().updateById(mpTemplateMessageLog);
    }

    /**
     * 微信公众号模版消息发送记录分页查询
     *
     * @param mpTemplateMessageLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<MpTemplateMessageLogVo> page(MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto);

    /**
     * 微信公众号模版消息发送记录导出数据
     *
     * @param mpTemplateMessageLogPageDto 微信公众号模版消息发送记录查询条件
     */
    void export(MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto);

}

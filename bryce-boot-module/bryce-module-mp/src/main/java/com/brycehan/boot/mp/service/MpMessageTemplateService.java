package com.brycehan.boot.mp.service;

import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.mp.entity.convert.MpMessageTemplateConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageTemplateDto;
import com.brycehan.boot.mp.entity.dto.MpMessageTemplatePageDto;
import com.brycehan.boot.mp.entity.po.MpMessageTemplate;
import com.brycehan.boot.mp.entity.vo.MpMessageTemplateVo;

/**
 * 微信公众号消息模板服务
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
public interface MpMessageTemplateService extends BaseService<MpMessageTemplate> {

    /**
     * 添加微信公众号消息模板
     *
     * @param mpMessageTemplateDto 微信公众号消息模板Dto
     */
    default void save(MpMessageTemplateDto mpMessageTemplateDto) {
        MpMessageTemplate mpMessageTemplate = MpMessageTemplateConvert.INSTANCE.convert(mpMessageTemplateDto);
        mpMessageTemplate.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(mpMessageTemplate);
    }

    /**
     * 更新微信公众号消息模板
     *
     * @param mpMessageTemplateDto 微信公众号消息模板Dto
     */
    default void update(MpMessageTemplateDto mpMessageTemplateDto) {
        MpMessageTemplate mpMessageTemplate = MpMessageTemplateConvert.INSTANCE.convert(mpMessageTemplateDto);
        this.getBaseMapper().updateById(mpMessageTemplate);
    }

    /**
     * 微信公众号消息模板分页查询
     *
     * @param mpMessageTemplatePageDto 查询条件
     * @return 分页信息
     */
    PageResult<MpMessageTemplateVo> page(MpMessageTemplatePageDto mpMessageTemplatePageDto);

    /**
     * 微信公众号消息模板导出数据
     *
     * @param mpMessageTemplatePageDto 微信公众号消息模板查询条件
     */
    void export(MpMessageTemplatePageDto mpMessageTemplatePageDto);

    /**
     * 同步公众号已添加的消息模板
     */
    void syncMpTemplate();

}

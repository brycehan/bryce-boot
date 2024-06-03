package com.brycehan.boot.mp.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.mp.entity.convert.MpMessageReplyRuleConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageReplyRuleDto;
import com.brycehan.boot.mp.entity.dto.MpMessageReplyRulePageDto;
import com.brycehan.boot.mp.entity.po.MpMessageReplyRule;
import com.brycehan.boot.mp.entity.vo.MpMessageReplyRuleVo;

import java.util.List;

/**
 * 微信公众号消息回复规则服务
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
public interface MpMessageReplyRuleService extends BaseService<MpMessageReplyRule> {

    /**
     * 添加微信公众号消息回复规则
     *
     * @param mpMessageReplyRuleDto 微信公众号消息回复规则Dto
     */
    default void save(MpMessageReplyRuleDto mpMessageReplyRuleDto) {
        MpMessageReplyRule mpMessageReplyRule = MpMessageReplyRuleConvert.INSTANCE.convert(mpMessageReplyRuleDto);
        mpMessageReplyRule.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(mpMessageReplyRule);
    }

    /**
     * 更新微信公众号消息回复规则
     *
     * @param mpMessageReplyRuleDto 微信公众号消息回复规则Dto
     */
    default void update(MpMessageReplyRuleDto mpMessageReplyRuleDto) {
        MpMessageReplyRule mpMessageReplyRule = MpMessageReplyRuleConvert.INSTANCE.convert(mpMessageReplyRuleDto);
        this.getBaseMapper().updateById(mpMessageReplyRule);
    }

    /**
     * 微信公众号消息回复规则分页查询
     *
     * @param mpMessageReplyRulePageDto 查询条件
     * @return 分页信息
     */
    PageResult<MpMessageReplyRuleVo> page(MpMessageReplyRulePageDto mpMessageReplyRulePageDto);

    /**
     * 微信公众号消息回复规则导出数据
     *
     * @param mpMessageReplyRulePageDto 微信公众号消息回复规则查询条件
     */
    void export(MpMessageReplyRulePageDto mpMessageReplyRulePageDto);

    /**
     * 筛选符合条件的回复规则
     *
     * @param exactMatch 是否精确匹配
     * @param keywords 关键词
     * @return 规则列表
     */
    List<MpMessageReplyRule> getMatchedRules(boolean exactMatch, String keywords);

}

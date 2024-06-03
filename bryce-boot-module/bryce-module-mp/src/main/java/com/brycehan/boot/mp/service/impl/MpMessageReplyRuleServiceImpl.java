package com.brycehan.boot.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.mp.entity.convert.MpMessageReplyRuleConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageReplyRulePageDto;
import com.brycehan.boot.mp.entity.po.MpMessageReplyRule;
import com.brycehan.boot.mp.mapper.MpMessageReplyRuleMapper;
import com.brycehan.boot.mp.service.MpMessageReplyRuleService;
import com.brycehan.boot.mp.entity.vo.MpMessageReplyRuleVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 微信公众号消息回复规则服务实现
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Service
@RequiredArgsConstructor
public class MpMessageReplyRuleServiceImpl extends BaseServiceImpl<MpMessageReplyRuleMapper, MpMessageReplyRule> implements MpMessageReplyRuleService {

    @Override
    public PageResult<MpMessageReplyRuleVo> page(MpMessageReplyRulePageDto mpMessageReplyRulePageDto) {
        IPage<MpMessageReplyRule> page = this.baseMapper.selectPage(getPage(mpMessageReplyRulePageDto), getWrapper(mpMessageReplyRulePageDto));
        return new PageResult<>(page.getTotal(), MpMessageReplyRuleConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param mpMessageReplyRulePageDto 微信公众号消息回复规则分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MpMessageReplyRule> getWrapper(MpMessageReplyRulePageDto mpMessageReplyRulePageDto){
        LambdaQueryWrapper<MpMessageReplyRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(mpMessageReplyRulePageDto.getMatchValue()), MpMessageReplyRule::getMatchValue, mpMessageReplyRulePageDto.getMatchValue());
        wrapper.eq(Objects.nonNull(mpMessageReplyRulePageDto.getStatus()), MpMessageReplyRule::getStatus, mpMessageReplyRulePageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(mpMessageReplyRulePageDto.getName()), MpMessageReplyRule::getName, mpMessageReplyRulePageDto.getName());
        return wrapper;
    }

    @Override
    public void export(MpMessageReplyRulePageDto mpMessageReplyRulePageDto) {
        List<MpMessageReplyRule> mpMessageReplyRuleList = this.baseMapper.selectList(getWrapper(mpMessageReplyRulePageDto));
        List<MpMessageReplyRuleVo> mpMessageReplyRuleVoList = MpMessageReplyRuleConvert.INSTANCE.convert(mpMessageReplyRuleList);
        ExcelUtils.export(MpMessageReplyRuleVo.class, "微信公众号消息回复规则_".concat(DateTimeUtils.today()), "微信公众号消息回复规则", mpMessageReplyRuleVoList);
    }

    @Override
    public List<MpMessageReplyRule> getMatchedRules(boolean exactMatch, String keywords) {

        LocalTime now = LocalTime.now();
        List<MpMessageReplyRule> validRules = getValidRules();

        return validRules.stream()
                // 检测是否在有效时段，effectTimeStart 为null则一直有效
                .filter(rule -> null == rule.getEffectTimeStart() || rule.getEffectTimeStart().isBefore(now))
                // 检测是否在有效时段，effectTimeEnd 为null则一直有效
                .filter(rule -> null == rule.getEffectTimeEnd() || rule.getEffectTimeEnd().isAfter(now))
                // 检测是否符合匹配规则
                .filter(rule -> isMatch(exactMatch || rule.getExactMatch(), keywords, rule.getMatchValue().split(",")))
                .collect(Collectors.toList());
    }

    /**
     * 检测文字是否匹配规则<br>
     * 精确匹配时，需关键词与规则词一致<br>
     * 非精确匹配时，检测文字需要包含任意一个规则词语
     *
     * @param exactMatch 是否精确匹配
     * @param checkWords 需要检测的文字
     * @param ruleWords 规则列表
     * @return 是否匹配
     */
    public boolean isMatch(boolean exactMatch, String checkWords, String[] ruleWords) {
        if (StringUtils.isBlank(checkWords)) {
            return false;
        }

        for (String ruleWord : ruleWords) {
            // 精确匹配
            if (exactMatch && checkWords.equalsIgnoreCase(ruleWord)) {
                return true;
            }
            // 模糊匹配
            if (!exactMatch && checkWords.equalsIgnoreCase(ruleWord)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取有效的规则列表
     *
     * @return 有效的规则列表
     */
    private List<MpMessageReplyRule> getValidRules() {
        LambdaQueryWrapper<MpMessageReplyRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MpMessageReplyRule::getStatus, 1);
        queryWrapper.isNotNull(MpMessageReplyRule::getMatchValue);
        queryWrapper.ne(MpMessageReplyRule::getMatchValue, "");
        queryWrapper.orderByDesc(MpMessageReplyRule::getPriority);

        return this.baseMapper.selectList(queryWrapper);
    }
}

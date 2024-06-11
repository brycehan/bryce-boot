package com.brycehan.boot.mp.service.impl;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageBatchDto;
import com.brycehan.boot.mp.entity.dto.MpUserPageDto;
import com.brycehan.boot.mp.entity.po.MpTemplateMessageLog;
import com.brycehan.boot.mp.entity.vo.MpUserVo;
import com.brycehan.boot.mp.service.MpTemplateMessageLogService;
import com.brycehan.boot.mp.service.MpTemplateMessageService;
import com.brycehan.boot.mp.service.MpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;


/**
 * 微信公众号模板消息服务实现
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpTemplateMessageServiceImpl implements MpTemplateMessageService {

    private final WxMpService wxMpService;
    private final MpUserService mpUserService;
    private final MpTemplateMessageLogService mpTemplateMessageLogService;

    /**
     * 发送信息模板消息
     * @param message 消息
     */
    @Async
    @Override
    public void sendTemplateMessage(WxMpTemplateMessage message) {
        String result;
        try {
            result = this.wxMpService.getTemplateMsgService().sendTemplateMsg(message);
        } catch (WxErrorException e) {
            result = e.getMessage();
        }

        this.mpTemplateMessageLogService.save(MpTemplateMessageLog.create(message, result));
    }

    @Async
    @Override
    public void sendMessageBatch(MpTemplateMessageBatchDto batchDto) {
        log.info("批量发送模板消息，参数：{}", JsonUtils.writeValueAsString(batchDto));
        WxMpTemplateMessage.WxMpTemplateMessageBuilder data = WxMpTemplateMessage.builder()
                .templateId(batchDto.getTemplateId())
                .url(batchDto.getUrl())
                .miniProgram(batchDto.getMiniProgram())
                .data(batchDto.getData());
        Map<String, Object> filterParams = batchDto.getMpUserFilterParams();
        int page = 1;
        MpUserPageDto mpUserPageDto = new MpUserPageDto();
        mpUserPageDto.setSize(500);
        mpUserPageDto.setCurrent(page);
        PageResult<MpUserVo> pageResult;
        do {
            pageResult = this.mpUserService.page(mpUserPageDto);
            pageResult.getList().forEach(mpUserVo -> {
                WxMpTemplateMessage message = data.toUser(mpUserVo.getOpenId()).build();
                this.sendTemplateMessage(message);
            });
            page += 1;
        } while (!CollectionUtils.isEmpty(Objects.requireNonNull(pageResult).getList()));

    }

}

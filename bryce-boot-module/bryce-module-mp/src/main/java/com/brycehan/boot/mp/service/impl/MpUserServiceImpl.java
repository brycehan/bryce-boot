package com.brycehan.boot.mp.service.impl;

import com.brycehan.boot.common.util.DateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.mp.convert.MpUserConvert;
import com.brycehan.boot.mp.dto.MpUserPageDto;
import com.brycehan.boot.mp.entity.MpUser;
import com.brycehan.boot.mp.vo.MpUserVo;
import com.brycehan.boot.mp.service.MpUserService;
import com.brycehan.boot.mp.mapper.MpUserMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * 微信公众号粉丝服务实现
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpUserServiceImpl extends BaseServiceImpl<MpUserMapper, MpUser> implements MpUserService {

    private final WxMpService wxMpService;

    private volatile static boolean syncMpUserTaskRunning = false;

    @Override
    public PageResult<MpUserVo> page(MpUserPageDto mpUserPageDto) {
        IPage<MpUser> page = this.baseMapper.selectPage(getPage(mpUserPageDto), getWrapper(mpUserPageDto));
        return new PageResult<>(page.getTotal(), MpUserConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param mpUserPageDto 微信公众号粉丝分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MpUser> getWrapper(MpUserPageDto mpUserPageDto){
        LambdaQueryWrapper<MpUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(mpUserPageDto.getCity()), MpUser::getCity, mpUserPageDto.getCity());
        wrapper.eq(StringUtils.isNotEmpty(mpUserPageDto.getQrSceneStr()), MpUser::getQrSceneStr, mpUserPageDto.getQrSceneStr());
        wrapper.like(StringUtils.isNotEmpty(mpUserPageDto.getNickname()), MpUser::getNickname, mpUserPageDto.getNickname());
        return wrapper;
    }

    @Override
    public void export(MpUserPageDto mpUserPageDto) {
        List<MpUser> mpUserList = this.baseMapper.selectList(getWrapper(mpUserPageDto));
        List<MpUserVo> mpUserVoList = MpUserConvert.INSTANCE.convert(mpUserList);
        ExcelUtils.export(MpUserVo.class, "微信公众号粉丝_".concat(DateTimeUtils.today()), "微信公众号粉丝", mpUserVoList);
    }

    @Override
    public MpUser refreshUserInfo(String openid) {
        try {
            log.info("更新用户信息，openid：{}",openid);
            WxMpUser wxMpUser = this.wxMpService.getUserService().userInfo(openid);
            if (wxMpUser == null) {
                log.error("获取不到用户信息，openid：{}", openid);
                return null;
            }
            MpUser mpUser = MpUser.create(wxMpUser);
            this.saveOrUpdate(mpUser);
            return mpUser;
        } catch (WxErrorException e) {
            log.error("更新用户信息失败，openid：{}", openid);
        }

        return null;
    }

    @Override
    public void refreshUserInfoAsync(String[] openidList) {
        log.info("批量更新用户信息");
        for (String openid : openidList) {
            CompletableFuture.runAsync(() -> this.refreshUserInfo(openid));
        }
    }

    /**
     * 根据openid更新用户信息
     *
     * @param openid openid
     */
    @Override
    public void unsubscribe(String openid) {
        MpUser mpUser = new MpUser();
        mpUser.setSubscribe(false);

        LambdaQueryWrapper<MpUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MpUser::getOpenId, openid);

        this.baseMapper.update(mpUser, queryWrapper);
    }

    @Async
    @Override
    public void syncMpUsers() {
        // 同步较慢，防止多次重复执行同步任务
        Assert.isTrue(!syncMpUserTaskRunning, "后台有同步任务正在处理，请稍后查看处理结果");
        syncMpUserTaskRunning = true;
        log.info("同步公众号粉丝列表");
        boolean hasMore = true;
        String nextOpenid = null;
        int page = 1;
        while (hasMore) {

            try {
                WxMpUserList userList = wxMpService.getUserService().userList(nextOpenid); // 拉取openid列表，每次最多1万个
                log.info("拉取openid列表：第{}页，数量：{}", page++, userList.getCount());
                List<String> openidList = userList.getOpenids();
                this.syncMpUsers(openidList);
                nextOpenid = userList.getNextOpenid();
                hasMore = StringUtils.isNotEmpty(nextOpenid) && userList.getCount() >= 10_000;
            } catch (WxErrorException e) {
                log.error("同步公众号粉丝出错：{}", e.getMessage());
            } finally {
                syncMpUserTaskRunning = false;
            }
        }

    }

    @Override
    public void syncMpUsers(List<String> openidList) {
        if (CollectionUtils.isEmpty(openidList)) {
            return;
        }

        WxMpUserService userService = this.wxMpService.getUserService();

        // 处理批次号，仅用于记录日志
        String batch = RandomStringUtils.randomNumeric(20);
        int batchSize = openidList.size();
        log.info("开始处理批次：{}，批次数量：{}", batch, batchSize);

        // 分批处理，每次最多拉取100个用户信息
        for (int i = 0; i < batchSize; i = i + 100) {
            final int finalStart = i;
            final int finalEnd = Math.min(i + 100, batchSize);
            final List<String> subOpenidList = openidList.subList(finalStart, finalEnd);

            CompletableFuture.runAsync(() -> {
                log.info("同步批次：{}-{}-{}，数量：{}", batch, finalStart, finalEnd, subOpenidList.size());
                try {
                    List<WxMpUser> wxMpUsers = userService.userInfoList(subOpenidList);
                    if (!CollectionUtils.isEmpty(wxMpUsers)) {
                        List<MpUser> mpUsers = wxMpUsers.parallelStream().map(MpUser::create).toList();
                        this.saveOrUpdateBatch(mpUsers);
                    }
                } catch (WxErrorException e) {
                    log.error("同步出错，批次：{}-{}-{}，数量：{}，错误信息：{}",
                            batch, finalStart, finalEnd, subOpenidList.size(), e.getMessage());
                }
            });
        }
    }
}

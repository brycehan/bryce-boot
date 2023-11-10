package com.brycehan.boot.wechat.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.wechat.convert.WechatAppConvert;
import com.brycehan.boot.wechat.dto.WechatAppDto;
import com.brycehan.boot.wechat.dto.WechatAppPageDto;
import com.brycehan.boot.wechat.entity.WechatApp;
import com.brycehan.boot.wechat.mapper.WechatAppMapper;
import com.brycehan.boot.wechat.service.WechatAppService;
import com.brycehan.boot.wechat.vo.WechatAppVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 微信应用服务实现
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Service
@RequiredArgsConstructor
public class WechatAppServiceImpl extends BaseServiceImpl<WechatAppMapper, WechatApp> implements WechatAppService {

    @Override
    public void save(WechatAppDto wechatAppDto) {
        // 检查 AppId 是否已存在
        LambdaQueryWrapper<WechatApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WechatApp::getAppId, wechatAppDto.getAppId());

        WechatApp app = this.baseMapper.selectOne(queryWrapper);

        if(app != null) {
            throw new RuntimeException("微信应用 AppID 已存在");
        }

        // 添加
        WechatApp wechatApp = WechatAppConvert.INSTANCE.convert(wechatAppDto);
        wechatApp.setId(IdGenerator.nextId());
        this.baseMapper.insert(wechatApp);
    }

    @Override
    public void update(WechatAppDto wechatAppDto) {
        // 检查 AppId 是否已存在
        LambdaQueryWrapper<WechatApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WechatApp::getAppId, wechatAppDto.getAppId());

        WechatApp app = this.baseMapper.selectOne(queryWrapper);

        if(app != null && !app.getId().equals(wechatAppDto.getId())) {
            throw new RuntimeException("微信应用 AppID 已存在");
        }

        // 更新
        WechatApp wechatApp = WechatAppConvert.INSTANCE.convert(wechatAppDto);
        this.baseMapper.updateById(wechatApp);
    }

    @Override
    public PageResult<WechatAppVo> page(WechatAppPageDto wechatAppPageDto) {

        IPage<WechatApp> page = this.baseMapper.selectPage(getPage(wechatAppPageDto), getWrapper(wechatAppPageDto));

        return new PageResult<>(page.getTotal(), WechatAppConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param wechatAppPageDto 微信应用分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<WechatApp> getWrapper(WechatAppPageDto wechatAppPageDto){
        LambdaQueryWrapper<WechatApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(wechatAppPageDto.getName()), WechatApp::getName, wechatAppPageDto.getName());
        wrapper.eq(StringUtils.isNotEmpty(wechatAppPageDto.getType()), WechatApp::getType, wechatAppPageDto.getType());
        wrapper.eq(Objects.nonNull(wechatAppPageDto.getTenantId()), WechatApp::getTenantId, wechatAppPageDto.getTenantId());
        wrapper.eq(Objects.nonNull(wechatAppPageDto.getStatus()), WechatApp::getStatus, wechatAppPageDto.getStatus());
        return wrapper;
    }

    @Override
    public void export(WechatAppPageDto wechatAppPageDto) {
        List<WechatApp> wechatAppList = this.baseMapper.selectList(getWrapper(wechatAppPageDto));
        List<WechatAppVo> wechatAppVoList = WechatAppConvert.INSTANCE.convert(wechatAppList);
        ExcelUtils.export(WechatAppVo.class, "微信应用_".concat(DateTimeUtils.today()), "微信应用", wechatAppVoList);
    }

    @Override
    public List<WechatAppVo> list(WechatAppDto wechatAppDto) {
        LambdaQueryWrapper<WechatApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(wechatAppDto.getAppId()), WechatApp::getAppId, wechatAppDto.getAppId());
        queryWrapper.eq(wechatAppDto.getTenantId() != null, WechatApp::getTenantId, wechatAppDto.getTenantId());

        List<WechatApp> wechatAppList = this.baseMapper.selectList(queryWrapper);

        return WechatAppConvert.INSTANCE.convert(wechatAppList);
    }

}

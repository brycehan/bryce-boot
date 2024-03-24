package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysNoticeConvert;
import com.brycehan.boot.system.dto.SysNoticePageDto;
import com.brycehan.boot.system.entity.SysNotice;
import com.brycehan.boot.system.mapper.SysNoticeMapper;
import com.brycehan.boot.system.service.SysNoticeService;
import com.brycehan.boot.system.vo.SysNoticeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统通知公告服务实现
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    @Override
    public PageResult<SysNoticeVo> page(SysNoticePageDto sysNoticePageDto) {
        IPage<SysNotice> page = this.baseMapper.selectPage(getPage(sysNoticePageDto), getWrapper(sysNoticePageDto));
        return new PageResult<>(page.getTotal(), SysNoticeConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysNoticePageDto 系统通知公告分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysNotice> getWrapper(SysNoticePageDto sysNoticePageDto) {
        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysNoticePageDto.getType()), SysNotice::getType, sysNoticePageDto.getType());
        wrapper.eq(Objects.nonNull(sysNoticePageDto.getStatus()), SysNotice::getStatus, sysNoticePageDto.getStatus());

        return wrapper;
    }

    @Override
    public void export(SysNoticePageDto sysNoticePageDto) {
        List<SysNotice> sysNoticeList = this.baseMapper.selectList(getWrapper(sysNoticePageDto));
        List<SysNoticeVo> sysNoticeVoList = SysNoticeConvert.INSTANCE.convert(sysNoticeList);
        ExcelUtils.export(SysNoticeVo.class, "系统通知公告_".concat(DateTimeUtils.today()), "系统通知公告", sysNoticeVoList);
    }

}

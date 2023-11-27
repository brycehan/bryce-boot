package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysTenantConvert;
import com.brycehan.boot.system.dto.SysTenantPageDto;
import com.brycehan.boot.system.entity.SysTenant;
import com.brycehan.boot.system.mapper.SysTenantMapper;
import com.brycehan.boot.system.service.SysTenantService;
import com.brycehan.boot.system.vo.SysTenantVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 系统租户服务实现
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends BaseServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Override
    public PageResult<SysTenantVo> page(SysTenantPageDto sysTenantPageDto) {
        IPage<SysTenant> page = this.baseMapper.selectPage(getPage(sysTenantPageDto), getWrapper(sysTenantPageDto));
        return new PageResult<>(page.getTotal(), SysTenantConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysTenantPageDto 系统租户分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysTenant> getWrapper(SysTenantPageDto sysTenantPageDto) {
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysTenantPageDto.getStatus()), SysTenant::getStatus, sysTenantPageDto.getStatus());

        if (sysTenantPageDto.getCreatedTimeStart() != null && sysTenantPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysTenant::getCreatedTime, sysTenantPageDto.getCreatedTimeStart(), sysTenantPageDto.getCreatedTimeEnd());
        } else if (sysTenantPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysTenant::getCreatedTime, sysTenantPageDto.getCreatedTimeStart());
        } else if (sysTenantPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysTenant::getCreatedTime, sysTenantPageDto.getCreatedTimeEnd());
        }

        wrapper.like(StringUtils.isNotEmpty(sysTenantPageDto.getName()), SysTenant::getName, sysTenantPageDto.getName());

        return wrapper;
    }

    @Override
    public void export(SysTenantPageDto sysTenantPageDto) {
        List<SysTenant> sysTenantList = this.baseMapper.selectList(getWrapper(sysTenantPageDto));
        List<SysTenantVo> sysTenantVoList = SysTenantConvert.INSTANCE.convert(sysTenantList);
        ExcelUtils.export(SysTenantVo.class, "系统租户_".concat(DateTimeUtils.today()), "系统租户", sysTenantVoList);
    }

}

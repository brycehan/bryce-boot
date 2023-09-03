package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.system.convert.SysPostConvert;
import com.brycehan.boot.system.dto.SysPostDto;
import com.brycehan.boot.system.dto.SysPostPageDto;
import com.brycehan.boot.system.mapper.SysPostMapper;
import com.brycehan.boot.system.service.SysPostService;
import com.brycehan.boot.system.vo.SysPostVo;
import com.brycehan.boot.system.entity.SysPost;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 系统岗位服务实现类
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    @Override
    public void save(SysPostDto sysPostDto) {
        SysPost sysPost = SysPostConvert.INSTANCE.convert(sysPostDto);
        sysPost.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysPost);
    }

    @Override
    public void update(SysPostDto sysPostDto) {
        SysPost sysPost = SysPostConvert.INSTANCE.convert(sysPostDto);
        this.baseMapper.updateById(sysPost);
    }

    @Override
    public PageResult<SysPostVo> page(SysPostPageDto sysPostPageDto) {

        IPage<SysPost> page =  this.baseMapper.selectPage(getPage(sysPostPageDto), getWrapper(sysPostPageDto));

        return new PageResult<>(page.getTotal(), SysPostConvert.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public void export(SysPostPageDto sysPostPageDto) {
        List<SysPost> sysPosts = this.baseMapper.selectList(getWrapper(sysPostPageDto));
        List<SysPostVo> sysPostVos = SysPostConvert.INSTANCE.convert(sysPosts);
        ExcelUtils.export(SysPostVo.class, "岗位表", "岗位", sysPostVos);
    }

    /**
     * 封装查询条件
     *
     * @param sysPostPageDto 系统岗位分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysPost> getWrapper(SysPostPageDto sysPostPageDto){
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(sysPostPageDto.getPostCode()), SysPost::getPostCode, sysPostPageDto.getPostCode());
        wrapper.like(StringUtils.isNotBlank(sysPostPageDto.getPostName()), SysPost::getPostName, sysPostPageDto.getPostName());
        wrapper.eq(Objects.nonNull(sysPostPageDto.getStatus()), SysPost::getStatus, sysPostPageDto.getStatus());
        return wrapper;
    }

    @Override
    public List<SysPost> selectPostsByUsername(String username) {
        return this.baseMapper.selectPostsByUsername(username);
    }
}

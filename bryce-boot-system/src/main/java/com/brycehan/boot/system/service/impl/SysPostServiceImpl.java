package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.VersionException;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysPostConvert;
import com.brycehan.boot.system.entity.dto.SysPostCodeDto;
import com.brycehan.boot.system.entity.dto.SysPostDto;
import com.brycehan.boot.system.entity.dto.SysPostPageDto;
import com.brycehan.boot.system.entity.po.SysPost;
import com.brycehan.boot.system.entity.vo.SysPostVo;
import com.brycehan.boot.system.mapper.SysPostMapper;
import com.brycehan.boot.system.service.SysPostService;
import com.brycehan.boot.system.service.SysUserPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 系统岗位服务实现
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final SysUserPostService sysUserPostService;

     /**
     * 添加系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    public void save(SysPostDto sysPostDto) {
        SysPost sysPost = SysPostConvert.INSTANCE.convert(sysPostDto);
        sysPost.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysPost);
    }

    public void update(SysPostDto sysPostDto) {
        SysPost sysPost = SysPostConvert.INSTANCE.convert(sysPostDto);

        // 更新
        int updated = this.baseMapper.updateById(sysPost);
        if (updated == 0) {
            throw new VersionException();
        }
    }

    @Override
    public void delete(IdsDto idsDto) {
        // 删除岗位
        this.baseMapper.deleteByIds(idsDto.getIds());

        // 删除岗位用户关系
        this.sysUserPostService.deleteByPostIds(idsDto.getIds());
    }

    @Override
    public PageResult<SysPostVo> page(SysPostPageDto sysPostPageDto) {
        IPage<SysPost> page = this.baseMapper.selectPage(sysPostPageDto.toPage(), getWrapper(sysPostPageDto));
        return new PageResult<>(page.getTotal(), SysPostConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysPostPageDto 系统岗位分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysPost> getWrapper(SysPostPageDto sysPostPageDto) {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysPostPageDto.getStatus()), SysPost::getStatus, sysPostPageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(sysPostPageDto.getName()), SysPost::getName, sysPostPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysPostPageDto.getCode()), SysPost::getCode, sysPostPageDto.getCode());

        return wrapper;
    }

    @Override
    public void export(SysPostPageDto sysPostPageDto) {
        List<SysPost> sysPostList = this.baseMapper.selectList(getWrapper(sysPostPageDto));
        List<SysPostVo> sysPostVoList = SysPostConvert.INSTANCE.convert(sysPostList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysPostVo.class, "岗位数据_".concat(today), "岗位数据", sysPostVoList);
    }

    @Override
    public List<SysPostVo> list(SysPostPageDto sysPostPageDto) {
        // 正常岗位列表
        sysPostPageDto.setStatus(StatusType.ENABLE);
        List<SysPost> sysPostList = this.baseMapper.selectList(getWrapper(sysPostPageDto));

        return SysPostConvert.INSTANCE.convert(sysPostList);
    }

    @Override
    public List<String> getPostNameList(List<Long> postIdList) {
        if (CollUtil.isEmpty(postIdList)) {
            return List.of();
        }

        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysPost::getName);
        queryWrapper.in(SysPost::getId, postIdList);
        return this.baseMapper.selectList(queryWrapper).stream().map(SysPost::getName).toList();
    }

    @Override
    public boolean checkCodeUnique(SysPostCodeDto sysPostCodeDto) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysPost::getCode, SysPost::getId)
                .eq(SysPost::getCode, sysPostCodeDto.getCode());
        SysPost sysPost = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同岗位编码同ID为编码唯一
        return Objects.isNull(sysPost) || Objects.equals(sysPostCodeDto.getId(), sysPost.getId());
    }

}

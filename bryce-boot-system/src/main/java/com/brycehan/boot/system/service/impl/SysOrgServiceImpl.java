package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.TreeUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysOrgConvert;
import com.brycehan.boot.system.dto.SysOrgDto;
import com.brycehan.boot.system.dto.SysOrgPageDto;
import com.brycehan.boot.system.entity.SysOrg;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.mapper.SysOrgMapper;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysOrgService;
import com.brycehan.boot.system.vo.SysOrgVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统机构服务实现类
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    private final SysUserMapper sysUserMapper;

    @Override
    public void update(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);

        // 上级机构不能为自身
        if (sysOrg.getId().equals(sysOrgDto.getParentId())) {
            throw new RuntimeException("上级机构不能为自身");
        }

        // 上级机构不能为下级
        List<Long> subOrgIds = getSubOrgIds(sysOrg.getId());
        if (subOrgIds.contains(sysOrg.getParentId())) {
            throw new RuntimeException("上级机构不能为下级");
        }

        this.baseMapper.updateById(sysOrg);
    }

    @Override
    public List<Long> getSubOrgIds(Long id) {
        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysOrg::getId, SysOrg::getParentId);
        // 所有机构的id、pid列表
        List<SysOrg> orgList = this.baseMapper.selectList(queryWrapper);

        // 递归查询所有子机构ID列表
        List<Long> subIds = new ArrayList<>();
        this.getTree(id, orgList, subIds);

        // 本机构也添加进去
        subIds.add(id);

        return subIds;
    }

    /**
     * 递归查询所有子机构ID列表
     *
     * @param id      当前机构ID
     * @param orgList 所有机构的列表
     * @param subIds  当前机构的子机构列表
     */
    private void getTree(Long id, List<SysOrg> orgList, List<Long> subIds) {
        for (SysOrg sysOrg : orgList) {
            if (sysOrg.getParentId().equals(id)) {
                this.getTree(sysOrg.getId(), orgList, subIds);

                subIds.add(sysOrg.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull)
                .toList();

        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 判断是否有子机构
        long orgCount = this.count(new LambdaQueryWrapper<SysOrg>().in(SysOrg::getParentId, ids));
        if (orgCount > 0) {
            throw new RuntimeException("请先删除子机构");
        }

        // 判断机构下面是否有用户
        long userCount = this.sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().in(SysUser::getOrgId, ids));
        if (userCount > 0) {
            throw new RuntimeException("机构下面有用户，不能删除");
        }

        this.baseMapper.deleteBatchIds(ids);
    }

    @Override
    public PageResult<SysOrgVo> page(SysOrgPageDto sysOrgPageDto) {

        IPage<SysOrg> page = this.baseMapper.selectPage(getPage(sysOrgPageDto), getWrapper(sysOrgPageDto));

        return new PageResult<>(page.getTotal(), SysOrgConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysOrgPageDto 系统机构分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysOrg> getWrapper(SysOrgPageDto sysOrgPageDto) {
        LambdaQueryWrapper<SysOrg> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysOrgPageDto.getStatus()), SysOrg::getStatus, sysOrgPageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysOrgPageDto.getTenantId()), SysOrg::getTenantId, sysOrgPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(sysOrgPageDto.getName()), SysOrg::getName, sysOrgPageDto.getName());
        return wrapper;
    }

    @Override
    public List<SysOrgVo> list(SysOrgDto sysOrgDto) {

        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(sysOrgDto.getName()), SysOrg::getName, sysOrgDto.getName());
        queryWrapper.eq(Objects.nonNull(sysOrgDto.getStatus()), SysOrg::getStatus, sysOrgDto.getStatus());
        queryWrapper.orderByAsc(SysOrg::getSort);
        List<SysOrg> list = this.baseMapper.selectList(queryWrapper);

        return TreeUtils.build(SysOrgConvert.INSTANCE.convert(list), 0L);
    }
}
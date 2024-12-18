package com.brycehan.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.SystemResponseStatus;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.util.TreeUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysOrgConvert;
import com.brycehan.boot.system.entity.dto.SysOrgDto;
import com.brycehan.boot.system.entity.po.SysOrg;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.vo.SysOrgVo;
import com.brycehan.boot.system.mapper.SysOrgMapper;
import com.brycehan.boot.system.service.SysOrgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统机构服务实现类
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    /**
     * 添加系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    public void save(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);
        sysOrg.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysOrg);
    }

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
    @Transactional
    public void delete(IdsDto idsDto) {
        // 判断是否有子机构
        long orgCount = this.count(new LambdaQueryWrapper<SysOrg>().in(SysOrg::getParentId, idsDto.getIds()));
        if (orgCount > 0) {
            throw new ServerException(SystemResponseStatus.ORG_LOWER_LEVEL_ORG_EXIST_CANNOT_BE_DELETED);
        }

        // 判断机构下面是否有用户
        Long userCount = Db.lambdaQuery(SysUser.class).in(SysUser::getOrgId, idsDto.getIds()).count();
        if (userCount > 0) {
            throw new RuntimeException("机构下面有用户，不能删除");
        }

        // 删除
        this.baseMapper.deleteByIds(idsDto.getIds());
    }

    @Override
    public List<SysOrgVo> list(SysOrgDto sysOrgDto) {
        Map<String, Object> params = BeanUtil.beanToMap(sysOrgDto, false, false);
        // 数据权限过滤
        params.put(DataConstants.DATA_SCOPE, getDataScope("bso", "id"));

        // 机构列表
        List<SysOrg> sysOrgList = this.baseMapper.list(params);
        return TreeUtils.build(SysOrgConvert.INSTANCE.convert(sysOrgList));
    }

    @Override
    public List<Long> getSubOrgIds(Long id) {
        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysOrg::getId, SysOrg::getParentId);

        // 所有机构的id、parentId列表
        List<SysOrg> orgList = this.baseMapper.selectList(queryWrapper);

        // 递归查询所有子机构IDs
        List<Long> subIds = new ArrayList<>();
        this.getTree(id, orgList, subIds);

        // 本机构也添加进去
        subIds.add(id);

        return subIds;
    }

    @Override
    public String getOrgNameById(Long orgId) {
        if (orgId != null) {
            LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysOrg::getName);
            queryWrapper.eq(SysOrg::getId, orgId);
            SysOrg sysOrg = this.baseMapper.selectOne(queryWrapper, false);
            if (sysOrg != null) {
                return sysOrg.getName();
            }
        }
        return "";
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

}

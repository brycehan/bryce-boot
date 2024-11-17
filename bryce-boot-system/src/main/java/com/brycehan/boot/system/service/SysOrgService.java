package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysOrgDto;
import com.brycehan.boot.system.entity.dto.SysOrgPageDto;
import com.brycehan.boot.system.entity.po.SysOrg;
import com.brycehan.boot.system.entity.vo.SysOrgVo;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统机构服务
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
public interface SysOrgService extends BaseService<SysOrg> {

    /**
     * 添加系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    void save(SysOrgDto sysOrgDto);

    /**
     * 更新系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    void update(SysOrgDto sysOrgDto);

    /**
     * 系统机构分页查询
     *
     * @param sysOrgPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysOrgVo> page(SysOrgPageDto sysOrgPageDto);

    /**
     * 列表查询
     *
     * @param sysOrgDto 查询参数
     * @return 机构列表
     */
    List<SysOrgVo> list(SysOrgDto sysOrgDto);

    /**
     * 根据机构ID，获取子机构ID列表（包含本机构ID）
     *
     * @param id 机构ID
     * @return 子机构ID列表
     */
    List<Long> getSubOrgIds(Long id);

    /**
     * 根据机构ID，获取机构名称
     *
     * @param orgId 机构ID
     * @return 机构名称
     */
    String getOrgNameById(Long orgId);

    /**
     * 根据机构ID列表，获取机构名称列表
     *
     * @param orgIds 机构ID列表
     * @return 机构ID机构名称列表map
     */
    default Map<Long, String> getOrgNamesByIds(List<Long> orgIds) {
        if (CollectionUtils.isEmpty(orgIds)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysOrg::getId, SysOrg::getName);
        queryWrapper.in(SysOrg::getId, orgIds);

        List<SysOrg> sysOrgList = getBaseMapper().selectList(queryWrapper);

        if (CollectionUtils.isEmpty(sysOrgList)) {
            return new HashMap<>();
        }

        return sysOrgList.stream().collect(Collectors.toMap(SysOrg::getId, SysOrg::getName));
    }

}

package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysOrgConvert;
import com.brycehan.boot.system.entity.dto.SysOrgDto;
import com.brycehan.boot.system.entity.dto.SysOrgPageDto;
import com.brycehan.boot.system.entity.po.SysOrg;
import com.brycehan.boot.system.entity.vo.SysOrgVo;

import java.util.List;

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
    default void save(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);
        sysOrg.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysOrg);
    }

    /**
     * 更新系统机构
     *
     * @param sysOrgDto 系统机构Dto
     */
    default void update(SysOrgDto sysOrgDto) {
        SysOrg sysOrg = SysOrgConvert.INSTANCE.convert(sysOrgDto);
        this.getBaseMapper().updateById(sysOrg);
    }

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

}

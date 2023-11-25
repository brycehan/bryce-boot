package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.convert.SysRoleConvert;
import com.brycehan.boot.system.dto.SysRoleDataScopeDto;
import com.brycehan.boot.system.dto.SysRoleDto;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.vo.SysRoleVo;

import java.util.List;

/**
 * 系统角色服务
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 添加系统角色
     *
     * @param sysRoleDto 系统角色Dto
     */
    default void save(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        sysRole.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysRole);
    }

    /**
     * 更新系统角色
     *
     * @param sysRoleDto 系统角色Dto
     */
    default void update(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        this.getBaseMapper().updateById(sysRole);
    }

    /**
     * 系统角色分页查询
     *
     * @param sysRolePageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysRoleVo> page(SysRolePageDto sysRolePageDto);

    /**
     * 系统角色导出数据
     *
     * @param sysRolePageDto 系统角色查询条件
     */
    void export(SysRolePageDto sysRolePageDto);

    /**
     * 角色列表查询
     *
     * @param sysRolePageDto 查询参数
     * @return 角色列表
     */
    List<SysRoleVo> list(SysRolePageDto sysRolePageDto);

    /**
     * 分配数据权限
     *
     * @param dataScopeDto 数据范围Dto
     */
    void dataScope(SysRoleDataScopeDto dataScopeDto);

}

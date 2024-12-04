package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.vo.SysRoleVo;

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
    void save(SysRoleDto sysRoleDto);

    /**
     * 更新系统角色
     *
     * @param sysRoleDto 系统角色Dto
     */
    void update(SysRoleDto sysRoleDto);

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
    void assignDataScope(SysRoleDataScopeDto dataScopeDto);

    /**
     * 获取角色名称列表
     *
     * @param roleIdList 角色ID列表
     * @return 角色名称列表
     */
    List<String> getRoleNameList(List<Long> roleIdList);

    /**
     * 分配/未分配 给用户的角色分页查询
     *
     * @param sysAssignRolePageDto 查询条件
     * @return 角色分页信息
     */
    PageResult<SysRoleVo> assignRolePage(SysAssignRolePageDto sysAssignRolePageDto);

    /**
     * 校验角色编码是否唯一
     *
     * @param sysRoleCodeDto 角色编码Dto
     * @return 是否唯一
     */
    boolean checkCodeUnique(SysRoleCodeDto sysRoleCodeDto);

}

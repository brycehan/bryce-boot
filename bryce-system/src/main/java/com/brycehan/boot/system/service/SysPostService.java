package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.dto.SysPostDto;
import com.brycehan.boot.system.dto.SysPostPageDto;
import com.brycehan.boot.system.entity.SysPost;
import com.brycehan.boot.system.vo.SysPostVo;

import java.util.List;

/**
 * 系统岗位服务
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
public interface SysPostService extends BaseService<SysPost> {

    /**
     * 添加系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    void save(SysPostDto sysPostDto);

    /**
     * 更新系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    void update(SysPostDto sysPostDto);

    /**
     * 系统岗位分页查询信息
     *
     * @param sysPostPageDto 系统岗位分页搜索条件
     * @return 分页信息
     */
    PageResult<SysPostVo> page(SysPostPageDto sysPostPageDto);

    /**
     * 系统岗位导出数据
     *
     * @param sysPostPageDto 系统岗位分页搜索条件
     */
    void export(SysPostPageDto sysPostPageDto);

    /**
     * 根据用户账号查询所属岗位组
     *
     * @param username 用户账号
     * @return 所属岗位组
     */
    List<SysPost> selectPostsByUsername(String username);

}

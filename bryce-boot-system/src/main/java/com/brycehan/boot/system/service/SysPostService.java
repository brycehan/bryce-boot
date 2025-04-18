package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysPostCodeDto;
import com.brycehan.boot.system.entity.dto.SysPostDto;
import com.brycehan.boot.system.entity.dto.SysPostPageDto;
import com.brycehan.boot.system.entity.po.SysPost;
import com.brycehan.boot.system.entity.vo.SysPostVo;

import java.util.Collection;
import java.util.List;

/**
 * 系统岗位服务
 *
 * @since 2022/10/31
 * @author Bryce Han
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
     * 系统岗位分页查询
     *
     * @param sysPostPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysPostVo> page(SysPostPageDto sysPostPageDto);

    /**
     * 系统岗位导出数据
     *
     * @param sysPostPageDto 系统岗位查询条件
     */
    void export(SysPostPageDto sysPostPageDto);

    /**
     * 岗位列表查询
     *
     * @param sysPostPageDto 分页参数
     * @return 岗位列表
     */
    List<SysPostVo> simpleList(SysPostPageDto sysPostPageDto);

    /**
     * 根据岗位ID列表查询岗位名称列表
     *
     * @param postIdList 岗位ID列表
     * @return 岗位名称列表
     */
    List<String> getPostNameList(List<Long> postIdList);

    /**
     * 根据岗位ID列表校验岗位列表是否存在
     *
     * @param postIds 岗位ID列表
     */
    void validatePostList(Collection<Long> postIds);

    /**
     * 检查岗位编码是否唯一
     *
     * @param sysPostCodeDto 岗位编码Dto
     * @return 是否唯一
     */
    boolean checkPostCodeUnique(SysPostCodeDto sysPostCodeDto);
}

package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmUserGroupDto;
import com.brycehan.boot.bpm.entity.dto.BpmUserGroupPageDto;
import com.brycehan.boot.bpm.entity.po.BpmUserGroup;
import com.brycehan.boot.bpm.entity.vo.BpmUserGroupVo;

import java.util.List;

/**
 * 用户组服务
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
public interface BpmUserGroupService extends BaseService<BpmUserGroup> {

    /**
     * 添加用户组
     *
     * @param bpmUserGroupDto 用户组Dto
     */
    void save(BpmUserGroupDto bpmUserGroupDto);

    /**
     * 更新用户组
     *
     * @param bpmUserGroupDto 用户组Dto
     */
    void update(BpmUserGroupDto bpmUserGroupDto);

    /**
     * 用户组分页查询
     *
     * @param bpmUserGroupPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmUserGroupVo> page(BpmUserGroupPageDto bpmUserGroupPageDto);

    /**
     * 用户组导出数据
     *
     * @param bpmUserGroupPageDto 用户组查询条件
     */
    void export(BpmUserGroupPageDto bpmUserGroupPageDto);

    /**
     * 用户组列表
     *
     * @param bpmUserGroupPageDto 用户组查询条件
     */
    List<BpmUserGroup> simpleList(BpmUserGroupPageDto bpmUserGroupPageDto);
}

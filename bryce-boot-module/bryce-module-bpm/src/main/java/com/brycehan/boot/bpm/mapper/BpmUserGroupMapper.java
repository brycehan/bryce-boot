package com.brycehan.boot.bpm.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.bpm.entity.po.BpmUserGroup;

/**
* 用户组Mapper接口
*
* @author Bryce Han
* @since 2025/03/08
*/
@Mapper
public interface BpmUserGroupMapper extends BryceBaseMapper<BpmUserGroup> {

}

package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.system.entity.SysUserPost;

/**
* 系统用户岗位关系Mapper接口
*
* @author Bryce Han
* @since 2023/09/30
*/
@Mapper
public interface SysUserPostMapper extends BryceBaseMapper<SysUserPost> {

}

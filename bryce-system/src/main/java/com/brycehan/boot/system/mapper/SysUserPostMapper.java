package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysUserPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户岗位关系Mapper接口
 *
 * @since 2023/09/30
 * @author Bryce Han
 */
@Mapper
public interface SysUserPostMapper extends BryceBaseMapper<SysUserPost> {

}

package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysUserPost;
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

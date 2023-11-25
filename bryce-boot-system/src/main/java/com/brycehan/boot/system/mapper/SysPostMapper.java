package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统岗位Mapper接口
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper
public interface SysPostMapper extends BryceBaseMapper<SysPost> {

}

package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysPost;
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

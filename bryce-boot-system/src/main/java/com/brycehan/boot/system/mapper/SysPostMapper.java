package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统岗位Mapper接口
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper
public interface SysPostMapper extends BryceBaseMapper<SysPost> {

    /**
     * 根据用户账号查询所属岗位组
     *
     * @param username 用户账号
     * @return 所属岗位组
     */
    List<SysPost> selectPostsByUsername(String username);

}

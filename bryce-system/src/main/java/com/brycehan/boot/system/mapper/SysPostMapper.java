package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.system.entity.SysPost;

import java.util.List;

/**
* 系统岗位Mapper接口
*
* @author Bryce Han
* @since 2023/09/28
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

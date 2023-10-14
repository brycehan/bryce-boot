package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.system.entity.SysNotice;

/**
* 系统通知公告Mapper接口
*
* @author Bryce Han
* @since 2023/10/13
*/
@Mapper
public interface SysNoticeMapper extends BryceBaseMapper<SysNotice> {

}

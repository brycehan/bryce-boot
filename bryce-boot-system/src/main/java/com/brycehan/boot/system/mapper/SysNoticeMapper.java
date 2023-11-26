package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统通知公告Mapper接口
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Mapper
public interface SysNoticeMapper extends BryceBaseMapper<SysNotice> {

}

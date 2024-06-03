package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysAttachment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统附件Mapper接口
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Mapper
public interface SysAttachmentMapper extends BryceBaseMapper<SysAttachment> {

}

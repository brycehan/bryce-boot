package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysAttachmentDto;
import com.brycehan.boot.system.entity.dto.SysAttachmentPageDto;
import com.brycehan.boot.system.entity.po.SysAttachment;
import com.brycehan.boot.system.entity.vo.SysAttachmentVo;

/**
 * 系统附件服务
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
public interface SysAttachmentService extends BaseService<SysAttachment> {

    /**
     * 添加系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    void save(SysAttachmentDto sysAttachmentDto);

    /**
     * 更新系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    void update(SysAttachmentDto sysAttachmentDto);

    /**
     * 系统附件分页查询
     *
     * @param sysAttachmentPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysAttachmentVo> page(SysAttachmentPageDto sysAttachmentPageDto);

    /**
     * 系统附件导出数据
     *
     * @param sysAttachmentPageDto 系统附件查询条件
     */
    void export(SysAttachmentPageDto sysAttachmentPageDto);

}

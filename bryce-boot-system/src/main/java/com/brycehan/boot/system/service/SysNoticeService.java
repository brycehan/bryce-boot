package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysNoticeConvert;
import com.brycehan.boot.system.entity.dto.SysNoticeDto;
import com.brycehan.boot.system.entity.dto.SysNoticePageDto;
import com.brycehan.boot.system.entity.po.SysNotice;
import com.brycehan.boot.system.entity.vo.SysNoticeVo;

/**
 * 系统通知公告服务
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
public interface SysNoticeService extends BaseService<SysNotice> {

    /**
     * 添加系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     */
    default void save(SysNoticeDto sysNoticeDto) {
        SysNotice sysNotice = SysNoticeConvert.INSTANCE.convert(sysNoticeDto);
        sysNotice.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysNotice);
    }

    /**
     * 更新系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     */
    default void update(SysNoticeDto sysNoticeDto) {
        SysNotice sysNotice = SysNoticeConvert.INSTANCE.convert(sysNoticeDto);
        this.getBaseMapper().updateById(sysNotice);
    }

    /**
     * 系统通知公告分页查询
     *
     * @param sysNoticePageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysNoticeVo> page(SysNoticePageDto sysNoticePageDto);

    /**
     * 系统通知公告导出数据
     *
     * @param sysNoticePageDto 系统通知公告查询条件
     */
    void export(SysNoticePageDto sysNoticePageDto);

}

package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysUploadFileConvert;
import com.brycehan.boot.system.dto.SysUploadFileDto;
import com.brycehan.boot.system.dto.SysUploadFilePageDto;
import com.brycehan.boot.system.entity.SysUploadFile;
import com.brycehan.boot.system.mapper.SysUploadFileMapper;
import com.brycehan.boot.system.service.SysUploadFileService;
import com.brycehan.boot.system.vo.SysUploadFileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 上传文件表服务实现类
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Service
@RequiredArgsConstructor
public class SysUploadFileServiceImpl extends BaseServiceImpl<SysUploadFileMapper, SysUploadFile> implements SysUploadFileService {

    @Override
    public void save(SysUploadFileDto sysUploadFileDto) {
        SysUploadFile sysUploadFile = SysUploadFileConvert.INSTANCE.convert(sysUploadFileDto);
        sysUploadFile.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysUploadFile);
    }

    @Override
    public void update(SysUploadFileDto sysUploadFileDto) {
        SysUploadFile sysUploadFile = SysUploadFileConvert.INSTANCE.convert(sysUploadFileDto);
        this.baseMapper.updateById(sysUploadFile);
    }

    @Override
    public PageResult<SysUploadFileVo> page(SysUploadFilePageDto sysUploadFilePageDto) {

        IPage<SysUploadFile> page = this.baseMapper.selectPage(getPage(sysUploadFilePageDto), getWrapper(sysUploadFilePageDto));

        return new PageResult<>(page.getTotal(), SysUploadFileConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysUploadFilePageDto 上传文件表分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysUploadFile> getWrapper(SysUploadFilePageDto sysUploadFilePageDto){
        LambdaQueryWrapper<SysUploadFile> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

}

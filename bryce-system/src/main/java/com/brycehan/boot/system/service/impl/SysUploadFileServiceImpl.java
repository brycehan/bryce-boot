package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.system.dto.SysUploadFilePageDto;
import com.brycehan.boot.system.entity.SysUploadFile;
import com.brycehan.boot.system.mapper.SysUploadFileMapper;
import com.brycehan.boot.system.service.SysUploadFileService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 上传文件服务实现类
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Service
public class SysUploadFileServiceImpl extends ServiceImpl<SysUploadFileMapper, SysUploadFile> implements SysUploadFileService {

    private final SysUploadFileMapper sysUploadFileMapper;

    public SysUploadFileServiceImpl(SysUploadFileMapper sysUploadFileMapper) {
        this.sysUploadFileMapper = sysUploadFileMapper;
    }

    /**
     * 查询多条数据
     *
     * @param sysUploadFilePageDto 分页查询数据对象
     * @return 对象列表
     */
    @Override
    public PageInfo<SysUploadFile> page(SysUploadFilePageDto sysUploadFilePageDto) {

        //1.根据page、size初始化分页参数，此时分页插件内部会将这两个参数放到ThreadLocal线程上下文中
        Page<SysUploadFile> page = startPage(sysUploadFilePageDto.getCurrent(), 1);

        /*
          2.紧跟初始化代码，查询业务数据
          2.1.分页拦截器根据page、size参数改写sql语句，生成count并查询
          2.2.分页拦截器执行实际的业务查询
          2.3.分页拦截器将2.1.的总记录数与2.2查询的分页数据封装到1中的page
          2.4.分页拦截器清空ThreadLocal上下文中记录的分页参数信息，防止内存泄漏
         */
        this.sysUploadFileMapper.page(sysUploadFilePageDto);

        //3.转换为PageInfo后返回
        return new PageInfo<>(page);
    }
}

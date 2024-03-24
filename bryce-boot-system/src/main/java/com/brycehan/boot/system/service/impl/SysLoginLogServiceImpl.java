package com.brycehan.boot.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.util.*;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysLoginLogConvert;
import com.brycehan.boot.system.dto.SysLoginLogPageDto;
import com.brycehan.boot.system.entity.SysLoginLog;
import com.brycehan.boot.system.mapper.SysLoginLogMapper;
import com.brycehan.boot.system.service.SysLoginLogService;
import com.brycehan.boot.system.vo.SysLoginLogVo;
import com.fhs.trans.service.impl.TransService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 系统登录日志服务实现
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    private final TransService transService;

    @Override
    public PageResult<SysLoginLogVo> page(SysLoginLogPageDto sysLoginLogPageDto) {
        IPage<SysLoginLog> page = this.baseMapper.selectPage(getPage(sysLoginLogPageDto), getWrapper(sysLoginLogPageDto));
        return new PageResult<>(page.getTotal(), SysLoginLogConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysLoginLogPageDto 系统登录日志分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysLoginLog> getWrapper(SysLoginLogPageDto sysLoginLogPageDto) {
        LambdaQueryWrapper<SysLoginLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysLoginLogPageDto.getStatus()), SysLoginLog::getStatus, sysLoginLogPageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(sysLoginLogPageDto.getUsername()), SysLoginLog::getUsername, sysLoginLogPageDto.getUsername());
        wrapper.like(StringUtils.isNotEmpty(sysLoginLogPageDto.getIp()), SysLoginLog::getIp, sysLoginLogPageDto.getIp());

        if (sysLoginLogPageDto.getAccessTimeStart() != null && sysLoginLogPageDto.getAccessTimeEnd() != null) {
            wrapper.between(SysLoginLog::getCreatedTime, sysLoginLogPageDto.getAccessTimeStart(), sysLoginLogPageDto.getAccessTimeEnd());
        } else if (sysLoginLogPageDto.getAccessTimeStart() != null) {
            wrapper.ge(SysLoginLog::getCreatedTime, sysLoginLogPageDto.getAccessTimeStart());
        } else if (sysLoginLogPageDto.getAccessTimeEnd() != null) {
            wrapper.ge(SysLoginLog::getCreatedTime, sysLoginLogPageDto.getAccessTimeEnd());
        }

        return wrapper;
    }

    @Override
    public void export(SysLoginLogPageDto sysLoginLogPageDto) {
        List<SysLoginLog> sysLoginLogList = this.baseMapper.selectList(getWrapper(sysLoginLogPageDto));
        List<SysLoginLogVo> sysLoginLogVoList = SysLoginLogConvert.INSTANCE.convert(sysLoginLogList);
        // 数据字典翻译
        this.transService.transBatch(sysLoginLogVoList);

        ExcelUtils.export(SysLoginLogVo.class, "系统登录日志_".concat(DateTimeUtils.today()), "系统登录日志", sysLoginLogVoList);
    }

    @Override
    public void save(String username, boolean status, Integer info) {
        HttpServletRequest request = ServletUtils.getRequest();

        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String ip = IpUtils.getIp(request);
        String location = LocationUtils.getLocationByIP(ip);

        UserAgent parser = UserAgentUtil.parse(userAgent);
        // 获取客户端浏览器
        String browser = parser.getBrowser().getName();
        // 获取客户端操作系统
        String os = parser.getOs().getName();

        // 封装对象
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setId(IdGenerator.nextId());
        loginLog.setUsername(username);
        loginLog.setStatus(status);
        loginLog.setInfo(info);
        loginLog.setIp(ip);
        loginLog.setLocation(location);
        loginLog.setUserAgent(userAgent);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);
        loginLog.setAccessTime(LocalDateTime.now());
        loginLog.setCreatedTime(LocalDateTime.now());

        // 保存数据
        this.baseMapper.insert(loginLog);
    }

}

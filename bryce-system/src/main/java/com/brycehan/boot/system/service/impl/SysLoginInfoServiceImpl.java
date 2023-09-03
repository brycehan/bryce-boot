package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blueconic.browscap.Capabilities;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.LocationUtils;
import com.brycehan.boot.common.util.UserAgentUtils;
import com.brycehan.boot.system.convert.SysLoginInfoConvert;
import com.brycehan.boot.system.dto.SysLoginInfoDto;
import com.brycehan.boot.system.dto.SysLoginInfoPageDto;
import com.brycehan.boot.system.entity.SysLoginInfo;
import com.brycehan.boot.system.mapper.SysLoginInfoMapper;
import com.brycehan.boot.system.service.SysLoginInfoService;
import com.brycehan.boot.system.vo.SysLoginInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 系统登录信息服务实现类
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLoginInfoServiceImpl extends BaseServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements SysLoginInfoService {

    @Override
    public void save(SysLoginInfoDto sysLoginInfoDto) {
        SysLoginInfo sysLoginInfo = SysLoginInfoConvert.INSTANCE.convert(sysLoginInfoDto);
        sysLoginInfo.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysLoginInfo);
    }

    @Override
    public void update(SysLoginInfoDto sysLoginInfoDto) {
        SysLoginInfo sysLoginInfo = SysLoginInfoConvert.INSTANCE.convert(sysLoginInfoDto);
        this.baseMapper.updateById(sysLoginInfo);
    }

    @Override
    public PageResult<SysLoginInfoVo> page(SysLoginInfoPageDto sysLoginInfoPageDto) {

        IPage<SysLoginInfo> page = this.baseMapper.selectPage(getPage(sysLoginInfoPageDto), getWrapper(sysLoginInfoPageDto));

        return new PageResult<>(page.getTotal(), SysLoginInfoConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysLoginInfoPageDto 系统登录信息表分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysLoginInfo> getWrapper(SysLoginInfoPageDto sysLoginInfoPageDto){
        LambdaQueryWrapper<SysLoginInfo> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void AsyncRecordLoginInfo(String userAgent, String ip, String username, Integer status, String message, Object... args) {
//        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
//        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        Capabilities capabilities = UserAgentUtils.parser.parse(userAgent);
        // 获取客户端浏览器
        String browser = capabilities.getBrowser();
        // 获取客户端操作系统
        String platform = capabilities.getPlatform();
        String realAddress = LocationUtils.getLocationByIP(ip);
        String info = Arrays.toString(ArrayUtils.toArray(ip)) +
                Arrays.toString(ArrayUtils.toArray(realAddress)) +
                Arrays.toString(ArrayUtils.toArray(username)) +
                Arrays.toString(ArrayUtils.toArray(status)) +
                Arrays.toString(ArrayUtils.toArray(message));

        log.info(info, args);

        // 封装对象
        SysLoginInfo loginInfo = new SysLoginInfo();
        loginInfo.setId(IdGenerator.nextId());
        loginInfo.setUsername(username);
        loginInfo.setIpaddr(ip);
        loginInfo.setLoginLocation(realAddress);
        loginInfo.setBrowser(browser);
        loginInfo.setOs(platform);
        loginInfo.setMessage(message);
        loginInfo.setStatus(status);
        loginInfo.setLoginTime(LocalDateTime.now());
        // 保存数据
        save(loginInfo);
    }
}

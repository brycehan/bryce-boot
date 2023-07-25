package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blueconic.browscap.Capabilities;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.service.IPAddressService;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.UserAgentUtils;
import com.brycehan.boot.system.dto.SysLoginInfoPageDto;
import com.brycehan.boot.system.entity.SysLoginInfo;
import com.brycehan.boot.system.mapper.SysLoginInfoMapper;
import com.brycehan.boot.system.service.SysLoginInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 系统登录信息服务实现类
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Slf4j
@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements SysLoginInfoService {

    private final SysLoginInfoMapper sysLoginInfoMapper;

    private final IPAddressService ipAddressService;

    public SysLoginInfoServiceImpl(SysLoginInfoMapper sysLoginInfoMapper, IPAddressService ipAddressService) {
        this.sysLoginInfoMapper = sysLoginInfoMapper;
        this.ipAddressService = ipAddressService;
    }

    /**
     * 查询多条数据
     *
     * @param sysLoginInfoPageDto 分页查询数据对象
     * @return 对象列表
     */
    @Override
    public PageInfo<SysLoginInfo> page(SysLoginInfoPageDto sysLoginInfoPageDto) {

        //1.根据page、size初始化分页参数，此时分页插件内部会将这两个参数放到ThreadLocal线程上下文中
        Page<SysLoginInfo> page = startPage(sysLoginInfoPageDto.getCurrent(), sysLoginInfoPageDto.getPageSize());

        /*
          2.紧跟初始化代码，查询业务数据
          2.1.分页拦截器根据page、size参数改写sql语句，生成count并查询
          2.2.分页拦截器执行实际的业务查询
          2.3.分页拦截器将2.1.的总记录数与2.2查询的分页数据封装到1中的page
          2.4.分页拦截器清空ThreadLocal上下文中记录的分页参数信息，防止内存泄漏
         */
        this.sysLoginInfoMapper.page(sysLoginInfoPageDto);

        //3.转换为PageInfo后返回
        return new PageInfo<>(page);
    }

    @Override
    public void AsyncRecordLoginInfo(String userAgent, String username, Integer status, String message, Object... args) {

        Capabilities capabilities = UserAgentUtils.parser.parse(userAgent);
        // 获取客户端浏览器
        String browser = capabilities.getBrowser();
        // 获取客户端操作系统
        String platform = capabilities.getPlatform();
        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        String realAddress = ipAddressService.getRealAddressByIP(ip);
        String info = Arrays.toString(ArrayUtils.toArray(ip)) +
                Arrays.toString(ArrayUtils.toArray(realAddress)) +
                Arrays.toString(ArrayUtils.toArray(username)) +
                Arrays.toString(ArrayUtils.toArray(status)) +
                Arrays.toString(ArrayUtils.toArray(message));

        log.info(info, args);

        // 封装对象
        SysLoginInfo loginInfo = new SysLoginInfo();
        loginInfo.setId(IdGenerator.generate());
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

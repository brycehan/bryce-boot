package com.brycehan.boot.monitor.vo;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统信息
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Data
public class Sys implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器IP地址
     */
    private String computerIp;

    public Sys() {
        this.setOsName(SystemUtil.getOsInfo().getName());
        this.setOsArch(SystemUtil.getOsInfo().getArch());
        this.setOsVersion(SystemUtil.getOsInfo().getVersion());
        this.setComputerName(OshiUtil.getOs().getNetworkParams().getHostName());
        this.setComputerIp(SystemUtil.getHostInfo().getAddress());
    }
}

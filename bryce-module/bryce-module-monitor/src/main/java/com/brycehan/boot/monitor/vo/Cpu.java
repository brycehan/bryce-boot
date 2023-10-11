package com.brycehan.boot.monitor.vo;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * CPU 信息
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Data
public class Cpu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 等待时间
     */
    private static final Long JOSHI_WAIT_SECOND = 360L;

    /**
     * CPU型号
     */
    private String cpuModel;

    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU 总的使用率
     */
    private double total;

    /**
     * CPU 系统使用率
     */
    private double sys;

    /**
     * CPU 用户使用率
     */
    private double used;

    /**
     * CPU 当前等待率
     */
    private double wait;

    /**
     * CPU 当前空闲率
     */
    private double free;

    public Cpu() {
        // 获取 CPU 相关信息，间隔1秒
        CpuInfo cpuInfo = OshiUtil.getCpuInfo(JOSHI_WAIT_SECOND);
        BeanUtils.copyProperties(cpuInfo, this);
        this.setCpuModel(cpuInfo.getCpuModel().split("\n")[0]);
    }
}

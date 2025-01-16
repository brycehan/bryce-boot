package com.brycehan.boot.monitor.entity.vo;

import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Data;
import oshi.hardware.GlobalMemory;

import java.io.Serial;
import java.io.Serializable;

/**
 * 内存信息
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Data
public class Mem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内存总数
     */
    private String total;

    /**
     * 已用内存
     */
    private String used;

    /**
     * 剩余内存
     */
    private String free;

    /**
     * 内存使用率
     */
    private String usage;

    public Mem() {
        GlobalMemory globalMemory = OshiUtil.getMemory();

        long used = globalMemory.getTotal() - globalMemory.getAvailable();

        setTotal(DataSizeUtil.format(globalMemory.getTotal()));
        setFree(DataSizeUtil.format(globalMemory.getAvailable()));
        setUsed(DataSizeUtil.format(used));
        setUsage(NumberUtil.formatPercent(NumberUtil.div(used, globalMemory.getTotal()), 2));
    }
}

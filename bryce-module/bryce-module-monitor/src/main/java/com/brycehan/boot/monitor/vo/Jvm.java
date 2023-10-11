package com.brycehan.boot.monitor.vo;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.SystemUtil;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Jvm 信息
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Data
public class Jvm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * JVM 最大可用内存总数
     */
    private String max;

    /**
     * JVM 占用的内存总数
     */
    private String total;

    /**
     * JVM 已用内存
     */
    private String used;

    /**
     * JVM 空闲内存
     */
    private String free;

    /**
     * JVM 内存使用率
     */
    private String usage;

    /**
     * JVM 名称
     */
    private String name;

    /**
     * Java 版本
     */
    private String version;

    /**
     * JVM 厂商
     */
    private String vendor;

    /**
     * JDK 路径
     */
    private String home;

    /**
     * Jar 路径
     */
    private String userDir;

    /**
     * JVM 启动时间
     */
    private String startedTime;

    /**
     * JVM 运行时间
     */
    private String runTime;

    /**
     * JVM 输入参数列表
     */
    private List<String> inputArguments;

    public Jvm() {
        this.setMax(DataSizeUtil.format(SystemUtil.getMaxMemory()));
        this.setTotal(DataSizeUtil.format(SystemUtil.getTotalMemory()));
        this.setFree(DataSizeUtil.format(SystemUtil.getFreeMemory()));
        this.setUsed(DataSizeUtil.format(SystemUtil.getTotalMemory() - SystemUtil.getFreeMemory()));
        this.setUsage(NumberUtil.formatPercent(NumberUtil.div(
                SystemUtil.getTotalMemory() - SystemUtil.getFreeMemory(), SystemUtil.getTotalMemory(), 4), 2));
        this.setName(SystemUtil.getRuntimeMXBean().getVmName());
        this.setVersion(SystemUtil.getJavaInfo().getVersion());
        this.setVendor(SystemUtil.getJavaInfo().getVendor());
        this.setHome(SystemUtil.getJavaRuntimeInfo().getHomeDir());
        this.setUserDir(SystemUtil.getUserInfo().getCurrentDir());
        Date startedTime = new Date(SystemUtil.getRuntimeMXBean().getStartTime());
        this.setStartedTime(DateUtil.formatDateTime(startedTime));
        this.setRunTime(DateUtil.formatBetween(startedTime, new Date(), BetweenFormatter.Level.SECOND));
        this.setInputArguments(SystemUtil.getRuntimeMXBean().getInputArguments());
    }

}

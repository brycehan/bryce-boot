package com.brycehan.boot.monitor.entity.vo;

import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.util.NumberUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 服务器信息
 *
 * @since 2023/10/10
 * @author Bryce Han
 */
@Slf4j
@Data
@Schema(description = "服务器信息")
public class Server implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * CPU 信息
     */
    @Schema(description = "CPU 信息")
    private Cpu cpu;

    /**
     * 内存信息
     */
    @Schema(description = "内存信息")
    private Mem mem;

    /**
     * JVM 信息
     */
    @Schema(description = "JVM 信息")
    private Jvm jvm;

    /**
     * 系统信息
     */
    @Schema(description = "系统信息")
    private Sys sys;

    /**
     * 磁盘分区信息
     */
    @Schema(description = "磁盘分区信息")
    private List<Disk> disks = new LinkedList<>();

    public Server() {
        cpu = new Cpu();
        mem = new Mem();
        jvm = new Jvm();
        sys = new Sys();
        initDisks();
        log.debug("服务器信息 {}", this);
    }

    /**
     * 初始化磁盘信息
     */
    private void initDisks() {
        SystemInfo systemInfo = new SystemInfo();
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();
        List<OSFileStore> fsList = fileSystem.getFileStores();
        for (OSFileStore fs : fsList) {
            Disk disk = new Disk();

            long used = fs.getTotalSpace() - fs.getFreeSpace();

            disk.setDiskName(fs.getName());
            disk.setDiskType(fs.getType());
            disk.setDirName(fs.getMount());
            disk.setTotal(DataSizeUtil.format(fs.getTotalSpace()));
            disk.setFree(DataSizeUtil.format(fs.getFreeSpace()));
            disk.setUsed(DataSizeUtil.format(used));
            disk.setUsage(NumberUtil.formatPercent(NumberUtil.div(used, fs.getTotalSpace()), 2));
            disks.add(disk);
        }
    }
}

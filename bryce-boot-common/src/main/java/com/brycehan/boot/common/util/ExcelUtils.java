package com.brycehan.boot.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.brycehan.boot.common.util.excel.ExcelDataListener;
import com.brycehan.boot.common.util.excel.ExcelFinishCallback;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Excel工具类
 * <br/>
 * <a href="https://easyexcel.opensource.alibaba.com/">https://easyexcel.opensource.alibaba.com/</a>
 *
 * @since 2023/4/23
 * @author Bryce Han
 */
@Slf4j
@SuppressWarnings("unused")
public class ExcelUtils {

    /**
     * 导出数据到文件
     *
     * @param head 实体类表头
     * @param filename 导出的文件名称
     * @param sheetName sheet 名称
     * @param data 数据列表
     * @param <T> 实体类型
     */
    public static <T> void export(Class<T> head, String filename, String sheetName, List<T> data){
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded + ".xlsx");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), head)
                    .useDefaultStyle(true) // 使用默认样式
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(StringUtils.isBlank(sheetName) ? "Sheet1": sheetName)
                    .doWrite(data);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出数据到文件
     *
     * @param head 实体类表头
     * @param file 文件对象
     * @param sheetName sheet 名称
     * @param data 数据列表
     * @param <T> 实体类型
     */
    public static <T> void export(Class<T> head, File file, String sheetName, List<T> data) {
        if (StrUtil.isBlank(sheetName)) {
            sheetName = "Sheet1";
        }
        try {
            EasyExcel.write(file, head).sheet(sheetName).doWrite(data);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取 Excel 文件
     *
     * @param file Excel 文件
     * @param head 列名类
     * @param <T> 数据类型
     * @return 数据列表
     */
    public static <T> List<T> read(MultipartFile file, Class<T> head) {
        return read(file, head, 1, 0);
    }

    /**
     * 读取 Excel 文件
     *
     * @param file Excel 文件
     * @param head 列名类
     * @param headRowNumber 表头行数
     * @param sheetNo sheet页
     * @param <T> 数据类型
     * @return 数据列表
     */
    public static <T> List<T> read(MultipartFile file, Class<T> head, Integer headRowNumber, Integer sheetNo) {
        try {
            return EasyExcel.read(file.getInputStream()).head(head).headRowNumber(headRowNumber).sheet(sheetNo).doReadSync();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取 Excel 文件
     *
     * @param file Excel 文件
     * @param head 列名类
     * @param callback 回调
     * @param <T> 数据类型
     */
    public static <T> void read(MultipartFile file, Class<T> head, ExcelFinishCallback<T> callback) {
        try {
            EasyExcel.read(file.getInputStream(), head, new ExcelDataListener<>(callback)).sheet().doRead();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * 读取 Excel 文件
     *
     * @param file Excel 文件
     * @param head 列名类
     * @param callback 回调
     * @param <T> 数据类型
     */
    public static <T> void read(File file, Class<T> head, ExcelFinishCallback<T> callback) {
        EasyExcel.read(file, head, new ExcelDataListener<>(callback)).sheet().doRead();
    }

}

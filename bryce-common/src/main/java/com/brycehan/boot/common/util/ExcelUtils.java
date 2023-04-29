package com.brycehan.boot.common.util;

import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Excel工具类
 *
 * @author Bryce Han
 * @since 2023/4/23
 */
public class ExcelUtils {

    /**
     * 导出数据到文件
     *
     * @param entity 实体类
     * @param filename 导出的文件名称
     * @param sheetName sheet 名称
     * @param data 数据列表
     * @param <T> 实体类型
     */
    public static <T> void export(Class<T> entity, String filename, String sheetName, List<T> data){
        try {
            HttpServletResponse response = HttpContextUtils.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + filenameEncoded + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), entity)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(StringUtils.isBlank(sheetName) ? "Sheet1": sheetName)
                    .doWrite(data);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}

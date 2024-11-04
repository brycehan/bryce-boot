package com.brycehan.boot.common.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.brycehan.boot.common.base.DictTransService;
import com.brycehan.boot.common.base.Trans;
import com.brycehan.boot.common.base.UnTrans;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.util.excel.ExcelDataListener;
import com.brycehan.boot.common.util.excel.ExcelFinishCallback;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Excel工具类
 *
 * @since 2023/4/23
 * @author Bryce Han
 */
@Slf4j
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
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(StringUtils.isBlank(sheetName) ? "Sheet1": sheetName)
                    .doWrite(data);
        }catch (IOException e){
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

    /**
     * 翻译字典数据到字段上
     * <p>比如 T 中有 gender 字段 为 M，需要给genderLabel字段自动设置为男</p>
     *
     * @param list 数据列表
     * @param <T> 数据类型
     */
    @SneakyThrows
    public static <T> void transList(List<T> list) {
        // 没有数据就不需要初始化
        if (CollUtil.isEmpty(list)) {
            return;
        }

        Class<?> clazz = list.get(0).getClass();
        // 拿到所有需要翻译的字段
        Field[] fields = ReflectUtil.getFields(clazz, field -> AnnotationUtil.hasAnnotation(field, Trans.class));

        DictTransService dictTransService = SpringUtil.getBean(DictTransService.class);
        for (T data : list) {
            for (Field field : fields) {
                field.setAccessible(true);
                Trans trans = field.getAnnotation(Trans.class);
                // dict 不能为空并且 ref 不为空的才处理
                if (StrUtil.isNotBlank(trans.dict()) && StrUtil.isNotBlank(trans.ref())) {
                    Field ref = ReflectUtil.getField(clazz, trans.ref());
                    ref.setAccessible(true);

                    // 获取字典值
                    String value = dictTransService.getDictTransMap().get(CacheConstants.SYSTEM_DICT_TRANS_KEY + trans.dict() + "_" + field.get(data));
                    if (StrUtil.isBlank(value)) {
                        continue;
                    }

                    // 一般目标字段string字段
                    ref.set(data, value);
                }
            }
        }
    }

    /**
     * 反向翻译，解析字典数据到字段上
     * <p>比如 T 中有 genderLabel 字段 为男，需要给gender字段自动设置为 M</p>
     *
     * @param list 数据列表
     * @param <T> 数据类型
     */
    @SneakyThrows
    public static <T> void unTransList(List<T> list) {
        // 没有数据就不需要初始化
        if (CollUtil.isEmpty(list)) {
            return;
        }

        Class<?> clazz = list.get(0).getClass();
        // 拿到所有需要反向翻译的字段
        Field[] fields = ReflectUtil.getFields(clazz, field -> AnnotationUtil.hasAnnotation(field, UnTrans.class));

        DictTransService dictTransService = SpringUtil.getBean(DictTransService.class);
        for (T data : list) {
            for (Field field : fields) {
                field.setAccessible(true);
                UnTrans unTrans = field.getAnnotation(UnTrans.class);
                // dict 不能为空并且 ref 不为空的才处理
                if (StrUtil.isNotBlank(unTrans.dict()) && StrUtil.isNotBlank(unTrans.ref())) {
                    Field ref = ReflectUtil.getField(clazz, unTrans.ref());
                    ref.setAccessible(true);

                    // 获取字典反向值
                    String value = dictTransService.getDictTransMap().get(CacheConstants.SYSTEM_DICT_UN_TRANS_KEY + unTrans.dict() + "_" + ref.get(data));
                    if (StrUtil.isBlank(value)) {
                        continue;
                    }

                    // 一般目标字段是boolean、int或者string字段，后面有添加单独抽离方法
                    if (Boolean.class.equals(field.getType())) {
                        field.set(data, Boolean.parseBoolean(value));
                    } else if (Integer.class.equals(field.getType())) {
                        field.set(data, Integer.parseInt(value));
                    }else {
                        field.set(data, value);
                    }
                }
            }
        }
    }

}

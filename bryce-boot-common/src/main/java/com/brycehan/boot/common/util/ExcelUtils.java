package com.brycehan.boot.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.brycehan.boot.common.base.excel.ExcelDataListener;
import com.brycehan.boot.common.base.excel.ExcelFinishCallback;
import com.fhs.common.utils.ConverterUtils;
import com.fhs.core.trans.anno.UnTrans;
import com.fhs.core.trans.constant.UnTransType;
import com.fhs.core.trans.util.ReflectUtils;
import com.fhs.core.trans.vo.TransPojo;
import com.fhs.trans.service.impl.DictionaryTransService;
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
     * @param entity 实体类
     * @param filename 导出的文件名称
     * @param sheetName sheet 名称
     * @param data 数据列表
     * @param <T> 实体类型
     */
    public static <T> void export(Class<T> entity, String filename, String sheetName, List<T> data){
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded + ".xlsx");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), entity)
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
     * 反向翻译，解析字典数据到字段上
     * <p>比如 T 中有 genderLabel 字段 为男，需要给gender字段自动设置为 1</p>
     *
     * @param list 数据列表
     * @param <T> 数据类型
     */
    @SneakyThrows
    public static <T extends TransPojo> void unTransList(List<T> list) {
        // 没有数据就不需要初始化
        if (CollUtil.isEmpty(list)) {
            return;
        }
        var UNTRANS_PREFIX = "un_trans:";

        Class<? extends TransPojo> clazz = list.get(0).getClass();
        // 拿到所有需要反向翻译的字段
        List<Field> fields = ReflectUtils.getAnnotationField(clazz, UnTrans.class);
        // 过滤出字典翻译
        fields = fields.stream().filter(field -> UnTransType.DICTIONARY.equals(field.getAnnotation(UnTrans.class).type()))
                .toList();
        DictionaryTransService dictionaryTransService = SpringUtil.getBean(DictionaryTransService.class);
        for (T data : list) {
            for (Field field : fields) {
                field.setAccessible(true);
                UnTrans unTrans = field.getAnnotation(UnTrans.class);
                // dict 不能为空并且 refs 不为空的才处理
                if (StrUtil.isNotBlank(unTrans.dict()) && StrUtil.isAllNotBlank(unTrans.refs())) {
                    Field ref = ReflectUtils.getDeclaredField(clazz, unTrans.refs()[0]);
                    ref.setAccessible(true);
                    // 获取字典反向值
                    String value = dictionaryTransService.getDictionaryTransMap().get(UNTRANS_PREFIX + unTrans.dict() + "_" + ref.get(data));
                    if (StrUtil.isBlank(value)) {
                        continue;
                    }

                    // 一般目标字段是boolean、int或者string字段，后面有添加单独抽离方法
                    if (Boolean.class.equals(field.getType())) {
                        field.set(data, ConverterUtils.toBoolean(value));
                    } else if (Integer.class.equals(field.getType())) {
                        field.set(data, ConverterUtils.toInteger(value));
                    }else {
                        field.set(data, ConverterUtils.toString(value));
                    }
                }
            }
        }
    }

}

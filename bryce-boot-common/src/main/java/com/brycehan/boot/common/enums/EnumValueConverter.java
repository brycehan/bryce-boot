package com.brycehan.boot.common.enums;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.brycehan.boot.common.util.AnnotationUtils;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 枚举类型转换器
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
@Slf4j
@SuppressWarnings("all")
public class EnumValueConverter implements Converter<Enum<?>> {

    /**
     * 枚举类型转 Excel 的单元格数据
     *
     * @param value 枚举值
     * @return Excel 的单元格数据
     */
    @Override
    public WriteCellData<?> convertToExcelData(Enum<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {

        Optional<Field> optionalField = AnnotationUtils.getAnnotationField(value.getClass(), JsonValue.class);

        // JsonValue注解不存在时使用枚举名称
        if (optionalField.isEmpty()) {
            return new WriteCellData<>(value.name());
        }

        // 枚举描述注解存在时反射获取枚举描述值
        Field field = optionalField.get();
        field.setAccessible(true);
        try {
            return new WriteCellData<>(String.valueOf(field.get(value)));
        } catch (Exception e) {
            log.error("反射获取枚举描述值失败", e);
        }
        return new WriteCellData<>();
    }

    /**
     * 字符串转枚举类型
     *
     * @param cellData 单元格数据
     * @return 枚举值
     */
    @Override
    public Enum<?> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Class<?> fieldClazz = contentProperty.getField().getType();
        Optional<Field> optionalField = AnnotationUtils.getAnnotationField(fieldClazz, JsonValue.class);

        if (optionalField.isEmpty()) {
            return null;
        }

        Field field = optionalField.get();
        field.setAccessible(true);

        for (Object enumConstant : fieldClazz.getEnumConstants()) {
            // 反射获取枚举对应的JsonValue值
            if (field.get(enumConstant).equals(cellData.getStringValue())) {
                return (Enum<?>) enumConstant;
            }
        }

        return null;
    }

}

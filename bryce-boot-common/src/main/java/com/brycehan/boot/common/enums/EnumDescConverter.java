package com.brycehan.boot.common.enums;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.brycehan.boot.common.util.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 枚举类型转换器
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
@SuppressWarnings("all")
public class EnumDescConverter implements Converter<Enum<?>> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Enum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Enum<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Optional<Field> optionalField = AnnotationUtils.getAnnotationField(value.getClass(), DescValue.class);

        // 枚举描述注解不存在时使用枚举名称
        if (optionalField.isEmpty()) {
            return new WriteCellData<>(value.name());
        }

        // 枚举描述注解存在时反射获取枚举描述值
        Field field = optionalField.get();
        field.setAccessible(true);
        return new WriteCellData<>(String.valueOf(field.get(value)));
    }

    @Override
    public Enum<?> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Class<?> fieldClazz = contentProperty.getField().getType();
        Optional<Field> optionalField = AnnotationUtils.getAnnotationField(fieldClazz, DescValue.class);

        if (optionalField.isEmpty()) {
            return null;
        }

        Field field = optionalField.get();
        field.setAccessible(true);

        for (var enumConstant : fieldClazz.getEnumConstants()) {
            // 反射获取枚举描述值
            if (field.get(enumConstant).equals(cellData.getStringValue())) {
                return (Enum<?>) enumConstant;
            }
        }

        return null;
    }
}

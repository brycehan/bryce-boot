package com.brycehan.boot.common.enums;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举类型转换器
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
@SuppressWarnings("unused")
public class EnumTypeValueConverter implements Converter<EnumType> {

    /**
     * 枚举类型转 Excel 的单元格数据
     *
     * @param value 枚举值
     * @return Excel 的单元格数据
     */
    @Override
    public WriteCellData<?> convertToExcelData(EnumType value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(String.valueOf(value.getValue()));
    }

    /**
     * 字符串转枚举类型
     *
     * @param cellData 单元格数据
     * @return 枚举值
     */
    @Override
    public EnumType convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 获取要处理的字段的clazz类型
        Class<?> fieldClazz = contentProperty.getField().getType();
        Method[] declaredMethods = fieldClazz.getDeclaredMethods();
        Optional<Method> getByValue = Arrays.stream(declaredMethods).filter(method -> method.getName().equals("getByValue")).findFirst();
        if (getByValue.isEmpty()) {
            return null;
        }

        // 获取 getByValue 方法和参数类型
        Method method = getByValue.get();
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 反射调用 getByValue 方法
        if (parameterTypes[0].isAssignableFrom(String.class)) {
            return (EnumType) method.invoke(cellData, cellData.getStringValue());
        }
        if (parameterTypes[0].isAssignableFrom(Integer.class)) {
            return (EnumType) method.invoke(cellData, cellData.getNumberValue().intValue());
        }

        throw new RuntimeException("不支持的枚举类型");
    }

}

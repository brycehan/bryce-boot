package com.brycehan.boot.common.enums;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.lang.reflect.Method;

/**
 * 枚举类型转换器
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
public class EnumTypeDescConverter implements Converter<EnumType> {

    /**
     * 枚举类型转 Excel 的单元格数据
     *
     * @param value 枚举值
     * @return Excel 的单元格数据
     */
    @Override
    public WriteCellData<?> convertToExcelData(EnumType value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.getDesc());
    }

    /**
     * 字符串转枚举类型
     *
     * @param cellData 单元格数据
     * @return 枚举值
     */
    @Override
    public EnumType convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Class<?> fieldClazz = contentProperty.getField().getType();
        Method getByDesc = fieldClazz.getMethod("getByDesc", String.class);
        return (EnumType) getByDesc.invoke(cellData, cellData.getStringValue());
    }

}

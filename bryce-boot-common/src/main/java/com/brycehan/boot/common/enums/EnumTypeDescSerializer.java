package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 枚举类型描述序列化器
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
public class EnumTypeDescSerializer extends JsonSerializer<EnumType> {
    @Override
    public void serialize(EnumType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getDesc());
    }
}

package com.brycehan.boot.common.enums;

import com.brycehan.boot.common.util.AnnotationUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 枚举类型描述序列化器
 *
 * @author Bryce Han
 * @since 2024/11/21
 */
@SuppressWarnings("unused")
public class EnumDescSerializer extends JsonSerializer<Enum<?>> {

    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var enumClazz = value.getClass();
        Optional<Field> optionalField = AnnotationUtils.getAnnotationField(enumClazz, DescValue.class);

        if (optionalField.isEmpty()) {
            gen.writeString(value.name());
            return;
        }

        Field field = optionalField.get();
        field.setAccessible(true);

        try {
            gen.writeString(String.valueOf(field.get(value)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

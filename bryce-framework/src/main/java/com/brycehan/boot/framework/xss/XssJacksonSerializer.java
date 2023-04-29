package com.brycehan.boot.framework.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * Cross Site Scripting 跨站脚本攻击JSON字符串响应结果处理
 *
 * @author Bryce Han
 * @since 2022/5/26
 */
public class XssJacksonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(StringEscapeUtils.escapeHtml4(value));
    }

}

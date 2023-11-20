package com.brycehan.boot.framework.common.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * Cross Site Scripting 跨站脚本攻击 JSON 字符串响应结果处理
 *
 * @since 2022/5/26
 * @author Bryce Han
 */
public class XssStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(StringEscapeUtils.escapeHtml4(value));
    }

}

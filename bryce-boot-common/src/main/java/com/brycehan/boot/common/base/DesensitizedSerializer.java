package com.brycehan.boot.common.base;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 数据脱敏序列化器
 *
 * @author Bryce Han
 * @since 2024/6/23
 */
public class DesensitizedSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private boolean useMasking = false;
    private DesensitizedType type;
    private int frontLength;
    private int endLength;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (useMasking && StringUtils.isNotEmpty(value)) {
            switch (type) {
                case USER_ID, CLEAR_TO_NULL -> gen.writeObject(null);
                case CHINESE_NAME -> gen.writeString(DesensitizedUtil.chineseName(value));
                case PHONE -> gen.writeString(DesensitizedUtil.mobilePhone(value));
                case FIXED_PHONE -> gen.writeString(DesensitizedUtil.fixedPhone(value));
                case EMAIL -> gen.writeString(DesensitizedUtil.email(value));
                case ID_CARD -> gen.writeString(DesensitizedUtil.idCardNum(value, 3, 4));
                case ADDRESS -> gen.writeString(DesensitizedUtil.address(value, frontLength));
                case PASSWORD -> gen.writeString("******");
                case CAR_LICENSE -> gen.writeString(DesensitizedUtil.carLicense(value));
                case BANK_CARD -> gen.writeString(DesensitizedUtil.bankCard(value));
                case IPV4 -> gen.writeString(DesensitizedUtil.ipv4(value));
                case IPV6 -> gen.writeString(DesensitizedUtil.ipv6(value));
                case FIRST_MASK -> gen.writeString(DesensitizedUtil.firstMask(value));
                case CLEAR_TO_EMPTY -> gen.writeString("");
                default -> gen.writeString(StrUtil.hide(value, frontLength, value.length() - endLength));
            }

        } else {
            gen.writeObject(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) {
        if (beanProperty != null) {
            Desensitized desensitized = beanProperty.getAnnotation(Desensitized.class);
            if (desensitized != null) {
                this.type = desensitized.type();
                this.frontLength = desensitized.frontLength();
                this.endLength = desensitized.endLength();
                this.useMasking = useMasking();
           }
        }
        return this;
    }

    /**
     * 是否使用脱敏
     * @return 是否使用脱敏
     */
    private boolean useMasking() {
        LoginUser loginUser = LoginUserContext.currentUser();
        return loginUser == null || !loginUser.isSuperAdmin();
    }

}

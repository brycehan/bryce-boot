package com.brycehan.boot.framework.operatelog.annotation;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.EnumType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 操作类型
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Getter
public enum OperatedType implements EnumType {

    INSERT(0, "新增"),
    UPDATE(1, "修改"),
    DELETE(2, "删除"),
    GET(3, "查询"),
    IMPORT(4, "导入"),
    EXPORT(5, "导出"),
    GRANT(6, "授权"),
    FORCE_QUIT(7, "强退"),
    GEN_CODE(8, "生成代码"),
    CLEAN_DATA(9, "清空数据"),
    OTHER(10, "其它");

    /**
     * 类型值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    OperatedType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static OperatedType getByDesc(String desc) {
        for (OperatedType type : values()) {
            if (type.getDesc().equals(desc)) {
                return type;
            }
        }
        return null;
    }

}

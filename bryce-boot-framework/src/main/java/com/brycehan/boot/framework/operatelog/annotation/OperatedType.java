package com.brycehan.boot.framework.operatelog.annotation;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 操作类型
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
public enum OperatedType {

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
    @DescValue
    private final String desc;

    /**
     * 根据描述获取枚举值
     *
     * @param desc 描述
     * @return 枚举值
     */
    public static OperatedType getByDesc(String desc) {
        for (OperatedType type : values()) {
            if (type.getDesc().equals(desc)) {
                return type;
            }
        }
        return null;
    }

}

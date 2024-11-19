package com.brycehan.boot.framework.operatelog.annotation;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
public enum OperatedType {

    INSERT(1, "新增"),
    UPDATE(2, "修改"),
    DELETE(3, "删除"),
    GET(4, "查询"),
    IMPORT(5, "导入"),
    EXPORT(6, "导出"),
    GRANT(7, "授权"),
    FORCE_QUIT(8, "强退"),
    GEN_CODE(9, "生成代码"),
    CLEAN_DATA(10, "清空数据"),
    OTHER(11, "其它");

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

}

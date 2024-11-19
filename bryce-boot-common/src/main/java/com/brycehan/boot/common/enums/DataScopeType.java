package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 数据范围类型
 *
 * @since 2022/5/8
 * @author Bryce Han
 */
@Getter
public enum DataScopeType {

    ALL(1, "全部数据"),
    ORG_AND_CHILDREN(2, "本机构及以下机构数据"),
    ORG_ONLY(3, "本机构数据"),
    SELF(4, "本人数据"),
    CUSTOM(5, "自定义数据");

    /**
     * 类型值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

     /**
     * 描述
     */
    private final String desc;

    DataScopeType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}

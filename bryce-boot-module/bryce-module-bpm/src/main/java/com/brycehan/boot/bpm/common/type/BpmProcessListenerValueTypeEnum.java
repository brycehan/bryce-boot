package com.brycehan.boot.bpm.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 流程监听器的值类型
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@AllArgsConstructor
public enum BpmProcessListenerValueTypeEnum {

    CLASS("class", "Java 类"),
    DELEGATE_EXPRESSION("delegateExpression", "代理表达式"),
    EXPRESSION("expression", "表达式");

    private final String type;
    private final String name;

}

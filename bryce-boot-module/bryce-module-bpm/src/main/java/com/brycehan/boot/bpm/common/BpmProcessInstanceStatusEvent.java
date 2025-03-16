package com.brycehan.boot.bpm.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

/**
 * 流程实例的状态（结果）发生变化的 Event
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@SuppressWarnings("ALL")
@Accessors(chain = true)
@Data
public class BpmProcessInstanceStatusEvent extends ApplicationEvent {

    /**
     * 流程实例的编号
     */
    @NotNull
    private String id;
    /**
     * 流程实例的 key
     */
    @NotNull
    private String processDefinitionKey;
    /**
     * 流程实例的结果
     */
    @NotNull
    private Integer status;
    /**
     * 流程实例对应的业务标识
     * 例如说，请假
     */
    private String businessKey;

    public BpmProcessInstanceStatusEvent(Object source) {
        super(source);
    }

}

package com.brycehan.boot.bpm.common;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;

/**
 * 流程实例事件发布者
 * <br>
 * <br>
 * {@link BpmProcessInstanceStatusEvent} 流程实例状态事件
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Validated
@AllArgsConstructor
public class BpmProcessInstanceEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void sendProcessInstanceResultEvent(@Valid BpmProcessInstanceStatusEvent event) {
        publisher.publishEvent(event);
    }

}

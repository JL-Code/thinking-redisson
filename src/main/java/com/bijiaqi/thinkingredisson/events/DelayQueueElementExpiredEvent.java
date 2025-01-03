package com.bijiaqi.thinkingredisson.events;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

/**
 * 延迟队列任务到期事件
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
@Getter
@Setter
@Accessors(chain = true)
public class DelayQueueElementExpiredEvent extends ApplicationEvent {
    private String eventType;
    private String taskId;

    public DelayQueueElementExpiredEvent(Object source) {
        super(source);
    }
}

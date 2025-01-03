package com.bijiaqi.thinkingredisson.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 延迟队列元素过期事件处理器
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
@Slf4j
@Component
public class DelayQueueElementExpiredEventProcessor {

    private final static String EVENT_TYPE = "DR_PAY_TIMEOUT";

    @EventListener
    public void handleDelayQueueElementExpiredEvent(DelayQueueElementExpiredEvent event) {
        log.info("handleDelayQueueElementExpiredEvent: {} {} {}",
                event.getEventType(),
                event.getTaskId(),
                event.getTimestamp());

        if (event.getEventType().equals(EVENT_TYPE)) {
            // TODO: 执行具体事件处理
        }
    }
}

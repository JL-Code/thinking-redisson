## 添加任务到延时队列

```java
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class Test {
    private final RedissonDelayQueue redissonDelayQueue;

    // 添加任务到延时队列
    public void addTask() {
        redissonDelayQueue.offer("DT_PAY_TIMEOUT:1848266749875625986", 10 * 60);
    }
}
```

## 消费延时队列中的元素过期事件

```java
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
```
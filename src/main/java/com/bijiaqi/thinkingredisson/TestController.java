package com.bijiaqi.thinkingredisson;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 * @see <a href="https://heapdump.cn/article/5341327">Redisson分布式延时队列 RedissonDelayedQueue</a>
 * <p>创建时间: 2024/10/22 </p>
 */
@Validated
@RequestMapping("/api/delayQueue")
@RequiredArgsConstructor
@RestController
public class TestController {

    private final RedissonClient redissonClient;
    private final AddTaskToDelayQueue addTaskToDelayQueue;

    /**
     * Take 死循环一直拿取
     */
    @GetMapping("/take")
    public void testRedissonDelayQueueTake() {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("orderQueue");
        // 注意虽然delayedQueue在这个方法里面没有用到，但是这行代码也是必不可少的。
        redissonClient.getDelayedQueue(blockingDeque);
        while (true) {
            String orderId;
            try {
                orderId = blockingDeque.take();
            } catch (Exception e) {
                System.err.println(Arrays.toString(e.getStackTrace()));
                continue;
            }

            if (!StringUtils.hasText(orderId)) {
                continue;
            }

            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "延时队列收到:" + orderId);
        }
    }

    /**
     * Offer 提供
     */
    @GetMapping("/offer")
    public void testRedissonDelayQueueOffer(@NotBlank String orderId) {
        addTaskToDelayQueue.addTaskToDelayQueue(orderId);
    }
}

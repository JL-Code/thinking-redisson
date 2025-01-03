package com.bijiaqi.thinkingredisson;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
@Component
@RequiredArgsConstructor
public class AddTaskToDelayQueue {
    private final RedissonClient redissonClient;

    /**
     * 添加任务到延时队列里面
     *
     * @param orderId 订单ID
     */
    public void addTaskToDelayQueue(String orderId) {
        // RBlockingDeque的实现类为:new RedissonBlockingDeque
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("orderQueue");
        // RDelayedQueue的实现类为:new RedissonDelayedQueue
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "添加任务到延时队列里面");
        delayedQueue.offer(orderId + "添加一个任务", 3, TimeUnit.SECONDS);
        delayedQueue.offer(orderId + "添加二个任务", 6, TimeUnit.SECONDS);
        delayedQueue.offer(orderId + "添加三个任务", 9, TimeUnit.SECONDS);
    }
}

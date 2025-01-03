package com.bijiaqi.thinkingredisson.service;

import jakarta.annotation.Resource;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 延时队列
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
@Service
public class RedissonDelayQueue {
    public static final String QUEUE = "redissonDelayQueue";

    /**
     * 默认超时时间，30秒
     */
    public static final Integer DEFAULT_TIMEOUT = 30;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 加入任务并设置到期时间
     *
     * @param element 元素 <eventType>:<id>:xxx:xxx:n 格式
     * @param timeout 元素延迟时间（秒）
     */
    public void offer(String element, Integer timeout) {
        boolean valid = DelayQueueAssist.checks(element);
        Assert.isTrue(!valid, "元素: " + element + " 无效的, 应该是：<eventType>:<id>:xxx:xxx:n 格式");
        RDelayedQueue<String> delayedQueue = delayedQueue();
        delayedQueue.offer(element, Objects.isNull(timeout) ? DEFAULT_TIMEOUT : timeout, TimeUnit.SECONDS);
    }

    /**
     * 移除任务
     *
     * @param element 元素
     */
    public void remove(String element) {
        RDelayedQueue<String> delayedQueue = delayedQueue();
        delayedQueue.removeIf(el -> el.equals(element));
    }

    /**
     * 任务列表
     *
     * @return 任务列表
     */
    public RDelayedQueue<String> delayedQueue() {
        RBlockingDeque<String> blockingDeque = blockingDeque();
        return redissonClient.getDelayedQueue(blockingDeque);
    }

    public RBlockingDeque<String> blockingDeque() {
        return redissonClient.getBlockingDeque(QUEUE);
    }

    public boolean isShutdown() {
        return redissonClient.isShutdown();
    }

    public void shutdown() {
        redissonClient.shutdown();
    }
}

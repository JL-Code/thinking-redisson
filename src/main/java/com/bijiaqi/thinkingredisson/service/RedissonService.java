package com.bijiaqi.thinkingredisson.service;

import com.bijiaqi.thinkingredisson.events.DelayQueueElementExpiredEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
@Service
@Slf4j
public class RedissonService implements ApplicationRunner {
    @Resource
    private ApplicationEventPublisher eventPublisher;
    @Resource
    private RedissonDelayQueue redissonQueue;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Override
    public void run(ApplicationArguments args) {
        RBlockingDeque<String> blockingDeque = redissonQueue.blockingDeque();
        executor.execute(() -> {
            while (true) {
                if (redissonQueue.isShutdown()) {
                    return;
                } else {
                    String element = null;
                    try {
                        element = blockingDeque.take();
                    } catch (InterruptedException e) {
                        log.warn("RedissonConsumer error:{}", e.getMessage());
                    }
                    if (StringUtils.hasText(element)) {
                        // TODO: 开始消费消息ID
                        log.info("timeout messageId : {}", element);
                        try {
                            var event = new DelayQueueElementExpiredEvent(element)
                                    .setEventType(DelayQueueAssist.parseEventType(element))
                                    .setTaskId(DelayQueueAssist.parseTaskId(element));
                            eventPublisher.publishEvent(event);
                        } catch (Exception e) {
                            log.error("publishEvent DelayQueueTaskExpiredEvent error:{}", e.getMessage());
                        }
                    }
                }
            }
        });

    }

    // 初始化，启动服务就执行一次
    @PostConstruct
    public void init() {
        redissonQueue.delayedQueue();
    }

    @PreDestroy
    public void shutdown() {
        redissonQueue.shutdown();
    }
}

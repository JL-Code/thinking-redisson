package com.bijiaqi.thinkingredisson;

import com.bijiaqi.thinkingredisson.service.RedissonDelayQueue;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
@RestController
@RequestMapping("/api/delayQueue/Tasks")
@RequiredArgsConstructor
public class Test2Controller {

    private final RedissonDelayQueue redissonDelayQueue;

    @PostMapping
    public ResponseEntity<?> add(@RequestParam(value = "taskId", required = false) String taskId,
                                 @RequestParam(value = "timeout", required = false) Integer timeout) {
        taskId = StringUtils.isEmpty(taskId) ? String.valueOf(UUID.randomUUID()) : taskId;
        redissonDelayQueue.offer(taskId, timeout);
        return ResponseEntity.ok().body(redissonDelayQueue.delayedQueue());
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<?> remove(@PathVariable("taskId") String taskId) {
        redissonDelayQueue.remove(taskId);
        return ResponseEntity.ok().body(redissonDelayQueue.delayedQueue());
    }

}

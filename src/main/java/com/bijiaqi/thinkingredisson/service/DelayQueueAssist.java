package com.bijiaqi.thinkingredisson.service;

import org.springframework.util.StringUtils;

/**
 * 延时队列帮助类
 * <p>创建时间: 2024/10/22 </p>
 *
 * @author <a href="mailto:jiangliu0316@outlook.com" rel="nofollow">蒋勇</a>
 */
public final class DelayQueueAssist {

    private final static String REGEX_DELIMITER = ":";
    private final static Integer EVENT_TYPE_INDEX = 0;
    private final static Integer TASK_ID_INDEX = 1;

    private DelayQueueAssist() {
    }

    public static String parseEventType(String element) {
        String[] splitList = element.split(REGEX_DELIMITER);
        return splitList[EVENT_TYPE_INDEX];
    }

    public static String parseTaskId(String element) {
        String[] splitList = element.split(REGEX_DELIMITER);
        return splitList[TASK_ID_INDEX];
    }

    /**
     * element 格式是否合法
     *
     * @param element <eventType>:<id>:xxx:xxx:n
     * @return true or false
     */
    public static boolean checks(String element) {
        if (!StringUtils.hasText(element)) {
            return false;
        }
        String[] splitList = element.split(REGEX_DELIMITER);
        return splitList.length > 2;
    }
}

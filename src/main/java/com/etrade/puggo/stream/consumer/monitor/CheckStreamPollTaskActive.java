package com.etrade.puggo.stream.consumer.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description StreamPollTask 监控任务
 * @Author niuzhenyu
 * @Date 2022/4/27 19:53
 **/
@Component
@Slf4j
public class CheckStreamPollTaskActive {

    private Subscription subscription;

    public void register(Subscription subscription) {
        this.subscription = subscription;
    }


    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void check() {

        // log.info("检查 StreamPollTask 任务状态开始...");

        if (!subscription.isActive()) {
            log.error("StreamPoolTask 任务中断，请及时处理");
        }

        log.info("检查 StreamPollTask 任务状态完成");

    }

}

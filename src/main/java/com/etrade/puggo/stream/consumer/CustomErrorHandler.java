package com.etrade.puggo.stream.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * StreamPollTask 获取消息或对应的listener消费消息过程中发生了异常
 *
 * @author huan.fu 2021/11/11 - 下午3:44
 */
@Slf4j
@Component
public class CustomErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.error("Stream队列发生了异常", t);

        // StreamPollTask继续轮询，每次轮询休眠 5s
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            log.error("休眠失败", e);
        }
    }

}

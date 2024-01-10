package com.etrade.puggo.stream.consumer;

import com.etrade.puggo.stream.consumer.StreamKeys.StreamMsgNewsKey;
import com.etrade.puggo.utils.RedisUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamInfo.XInfoStream;
import org.springframework.data.redis.connection.stream.StreamRecords;

/**
 * @author niuzhenyu
 * @description : SpringBoot 启动之前执行，初始化Redis Stream
 * @date 2023/6/27 17:03
 **/
@Slf4j
@Configuration
public class StreamInitialize {

    @Resource
    private RedisUtils redisUtils;

    @PostConstruct
    public void init() {

        log.info("开始初始化 Redis Stream...");

        XInfoStream info = null;

        try {

            info = redisUtils.info(StreamMsgNewsKey.KEY);

        } catch (RedisSystemException e) {

            String message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();

            assert message != null;
            if (message.contains("ERR no such key")) {
                // 创建 stream
                createStream(StreamMsgNewsKey.KEY);
                log.info("stream不存在，创建stream完成");

                createGroup(StreamMsgNewsKey.KEY, StreamMsgNewsKey.GROUP);
                log.info("stream group不存在，创建group完成");
            }
        }

        if (info != null && info.groupCount() == 0L) {
            createGroup(StreamMsgNewsKey.KEY, StreamMsgNewsKey.GROUP);
            log.info("stream group不存在，创建group完成");
        }

        log.info("Redis Stream 初始化完成");
    }


    private void createGroup(String key, String groupName) {
        redisUtils.createGroup(key, groupName);
    }

    private void createStream(String key) {
        ObjectRecord<String, String> record = StreamRecords.newRecord()
            .in(key)
            .ofObject("init stream")
            .withId(RecordId.autoGenerate());
        RecordId recordId = redisUtils.addStream(record);
        log.info("init stream recordId {}", recordId);
    }


}

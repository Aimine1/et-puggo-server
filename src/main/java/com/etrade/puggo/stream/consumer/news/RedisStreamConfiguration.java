package com.etrade.puggo.stream.consumer.news;

import com.etrade.puggo.stream.consumer.CustomErrorHandler;
import com.etrade.puggo.stream.consumer.StreamKeys.StreamMsgNewsKey;
import com.etrade.puggo.stream.consumer.monitor.CheckStreamPollTaskActive;
import com.etrade.puggo.third.im.pojo.MsgNewsDO;
import java.time.Duration;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.ConsumerStreamReadRequest;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamReadRequest;
import org.springframework.data.redis.stream.Subscription;

/**
 * redis stream 消费组配置
 *
 * @author huan.fu 2021/11/11 - 下午12:22
 */
@Configuration
public class RedisStreamConfiguration {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;
    @Resource
    private MsgNewsConsumeStreamListener msgNewsConsumeStreamListener;
    @Resource
    private CheckStreamPollTaskActive checkStreamPollTaskActive;

    /**
     * 可以同时支持 独立消费 和 消费者组 消费
     * <p>
     * 可以支持动态的 增加和删除 消费者
     * <p>
     * 消费组需要预先创建出来
     *
     * @return StreamMessageListenerContainer
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public StreamMessageListenerContainer<String, ObjectRecord<String, MsgNewsDO>> streamMessageListenerContainer() {
        AtomicInteger index = new AtomicInteger(1);
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("async-stream-consumer-" + index.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        });

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, MsgNewsDO>> options =
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                // 一次最多获取多少条消息
                .batchSize(10)
                // 运行 Stream 的 poll task
                .executor(executor)
                // 可以理解为 Stream Key 的序列化方式
                .keySerializer(RedisSerializer.string())
                // 可以理解为 Stream 后方的字段的 key 的序列化方式
                .hashKeySerializer(RedisSerializer.string())
                // 可以理解为 Stream 后方的字段的 value 的序列化方式
                .hashValueSerializer(RedisSerializer.string())
                // Stream 中没有消息时，阻塞多长时间，需要比 `spring.redis.timeout` 的时间小
                .pollTimeout(Duration.ofSeconds(1))
                // ObjectRecord 时，将 对象的 filed 和 value 转换成一个 Map 比如：将Book对象转换成map
                .objectMapper(new ObjectHashMapper())
                // 获取消息的过程或获取到消息给具体的消息者处理的过程中，发生了异常的处理
                .errorHandler(new CustomErrorHandler())
                // 将发送到Stream中的Record转换成ObjectRecord，转换成具体的类型是这个地方指定的类型
                .targetType(MsgNewsDO.class)
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, MsgNewsDO>> streamMessageListenerContainer =
            StreamMessageListenerContainer.create(redisConnectionFactory, options);

        msgNewsConsumeStreamListener.setConsumerType(StreamMsgNewsKey.CONSUMER_TYPE);
        msgNewsConsumeStreamListener.setGroup(StreamMsgNewsKey.GROUP);
        msgNewsConsumeStreamListener.setConsumerName(StreamMsgNewsKey.CONSUMER);

        // 消费组消费,不自动ack，示例1
        // 从消费组中没有分配给消费者的消息开始消费
        /*streamMessageListenerContainer
            .receive(Consumer.from(PrintRecord.GROUP, PrintRecord.CONSUMER),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                printRecordConsumeStreamListener);
        // 独立消费，示例2
        streamMessageListenerContainer
            .receive(StreamOffset.fromStart(streamKey), printRecordConsumeStreamListener);*/

        // 如果需要对某个消费者进行个性化配置在调用register方法的时候传递`StreamReadRequest`对象

        ConsumerStreamReadRequest<String> readRequest = StreamReadRequest
            .builder(StreamOffset.create(StreamMsgNewsKey.KEY, ReadOffset.lastConsumed()))
            .consumer(Consumer.from(StreamMsgNewsKey.GROUP, StreamMsgNewsKey.CONSUMER))
            .cancelOnError(throwable -> {
                // 如果发生运行异常，StreamPollTask 任务状态继续保持为 RUNNING
                return !(throwable instanceof RuntimeException);
            })
            // 不自动ack
            .autoAcknowledge(false)
            .build();

        // 在运行时抛出异常，不取消任务执行
        Subscription subscription = streamMessageListenerContainer.register(readRequest, msgNewsConsumeStreamListener);

        // 注册 StreamPollTask 监控任务
        checkStreamPollTaskActive.register(subscription);

        return streamMessageListenerContainer;
    }
}

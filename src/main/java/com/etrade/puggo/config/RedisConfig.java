package com.etrade.puggo.config;

import java.net.UnknownHostException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author niuzhenyu
 * @description : Redis 配置文件
 * @date 2023/5/22 22:14
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
        throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置其他的k-v的默认的序列化
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        //单独设置k的序列化
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }

}

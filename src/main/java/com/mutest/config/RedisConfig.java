package com.mutest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2018/11/26 9:28
 * @description redis配置，集群下启动session共享，需打开@EnableRedisHttpSession
 * @modify
 */
@Configuration
public class RedisConfig {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) {
		RedisTemplate redis = new RedisTemplate();
		GenericToStringSerializer<String> keySerializer = new GenericToStringSerializer<String>(String.class);
		redis.setKeySerializer(keySerializer);
		redis.setHashKeySerializer(keySerializer);
		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
		redis.setValueSerializer(valueSerializer);
		redis.setHashValueSerializer(valueSerializer);
		redis.setConnectionFactory(connectionFactory);

		return redis;
	}
}

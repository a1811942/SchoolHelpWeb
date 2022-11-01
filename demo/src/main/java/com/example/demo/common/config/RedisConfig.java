package com.example.demo.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis配置 配置序列化方式以及缓存管理器
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		//设置键的序列化方式
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//设置值的序列化方式
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));


		return  redisTemplate;
	}

}

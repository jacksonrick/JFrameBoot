package com.jf.system.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jf.commons.LogManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring Redis缓存
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 10:38
 */
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 缓存模板
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        LogManager.info("initializing redisTemplate...", getClass());
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);

        // 设置序列化工具 jackson2
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 序列化设置

        // 1.redisTemplate注入
        // key-value(默认)
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        // hash
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 2.注解(@Cacheable)
        //template.setValueSerializer(jackson2JsonRedisSerializer);

        // 3.同时开启
        // 需要配置两个RedisTemplate(bean-name不同)

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 支持缓存注解
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        LogManager.info("initializing redisCacheManager...", getClass());
        // 初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer())) //设置序列化
                .entryTtl(Duration.ofHours(1)); // 设置默认超过期时间

        // 根据cacheName设置不同的过期时间
        // Map<String, RedisCacheConfiguration> redisCacheConfigurationMap
        // new RedisCacheManager(redisCacheWriter, redisCacheConfiguration, redisCacheConfigurationMap);

        // 初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return cacheManager;
    }

}

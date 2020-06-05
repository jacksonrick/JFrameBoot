package com.jf.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring Redis缓存
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 10:38
 */
@Component
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * 缓存模板
     *
     * <p>适用于复杂对象的保存
     * private RedisTemplate redisTemplate;
     * </p>
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置序列化工具 jackson2
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 序列化设置
        // 默认
        // template.setDefaultSerializer(jackson2JsonRedisSerializer);
        // key-value
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * <p>适用于简单键值对的保存
     * private StringRedisTemplate stringRedisTemplate;
     * </p>
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 配置缓存注解
     * 支持注解：@Cacheable(cacheNames = "", key = "'xx'+#id")、@CacheEvict(value = "", key = "'xx'+#id")
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        // 初始化RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());

        // ·设置默认的key前缀
        // ·设置序列化方式
        // ·设置过期时间，如果不设置，会使用spring.redis.time-to-live
        CacheKeyPrefix cacheKeyPrefix = name -> name + ":";
        RedisCacheConfiguration defaultRedisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .computePrefixWith(cacheKeyPrefix)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofHours(1));
        RedisCacheConfiguration redisCacheConfiguration1 = RedisCacheConfiguration
                .defaultCacheConfig()
                .computePrefixWith(cacheKeyPrefix)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofSeconds(100));
        RedisCacheConfiguration redisCacheConfiguration2 = RedisCacheConfiguration
                .defaultCacheConfig()
                .computePrefixWith(cacheKeyPrefix)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofMinutes(5));

        // 根据cacheName设置不同的序列化方式和过期时间
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("c1", redisCacheConfiguration1);
        redisCacheConfigurationMap.put("c2", redisCacheConfiguration2);

        // 初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultRedisCacheConfiguration, redisCacheConfigurationMap);
        return cacheManager;
    }

}

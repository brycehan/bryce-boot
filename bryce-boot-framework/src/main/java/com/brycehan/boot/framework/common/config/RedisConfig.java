package com.brycehan.boot.framework.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Objects;

/**
 * Redis配置
 *
 * @since 2023/5/8
 * @author Bryce Han
 */
@Configuration
public class RedisConfig {

    /**
     * Jackson2JsonRedisSerializer序列化器
     *
     * @return Jackson2JsonRedisSerializer
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.deactivateDefaultTyping();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    /**
     * RedisTemplate配置
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Key、HashKey使用String来序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // Value、HashValue使用Jackson2JsonRedisSerializer来序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * RedisCacheManager配置
     *
     * @param redisTemplate RedisTemplate
     * @return RedisCacheManager
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        return new CustomRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * RedissonClient配置
     *
     * @param redisProperties RedisProperties
     * @return RedissonClient
     */
    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        // 哨兵模式
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        if (sentinel != null) {
            config.useSentinelServers()
                    .addSentinelAddress(sentinel.getNodes().toArray(new String[0]))
                    .setMasterName(sentinel.getMaster())
                    .setDatabase(redisProperties.getDatabase())
                    .setUsername(redisProperties.getUsername())
                    .setPassword(redisProperties.getPassword());
            return Redisson.create(config);
        }
        // 集群模式
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (cluster != null) {
            config.useClusterServers()
                    .addNodeAddress(redisProperties.getCluster().getNodes().toArray(new String[0]))
                    .setPassword(redisProperties.getPassword());
            return Redisson.create(config);
        }

        // 单机模式
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setDatabase(redisProperties.getDatabase())
                .setUsername(redisProperties.getUsername())
                .setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }

}

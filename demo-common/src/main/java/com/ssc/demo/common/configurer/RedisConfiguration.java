/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.ssc.demo.common.configurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfiguration extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int dataBase;

    @Resource
    private RedisStringKeySerializer redisStringKeySerializer;


    /**
     * 连接池配置信息
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(maxIdle);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(minIdle);
        //当池内没有可用的连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(dataBase);
        redisStandaloneConfiguration.setPort(port);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //设置redis的服务的端口号
//        redisStandaloneConfiguration.setPort(port);
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        log.info("redisTemplate注入成功！！");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(redisStringKeySerializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory(jedisPoolConfig()));
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate() {
        log.info("stringRedisTemplate注入成功！！");
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setKeySerializer(redisStringKeySerializer);
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory(jedisPoolConfig()));
        return stringRedisTemplate;
    }

    /**
     * 管理缓存
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String,Object> redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                // key序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisStringKeySerializer))
                // 去掉双冒号 ::
                .computePrefixWith(cacheName -> cacheName);
        return new CustomRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * 注解缓存 {@link org.springframework.cache.annotation.Cacheable} keyGenerator生成
     * keyGenerator 和 key 冲突，key 优先级较高
     * @return {@link KeyGenerator}
     * @author Administrator
     * @date 2021/10/27 10:14
     * @version v1.0
     * @change 2021/10/27 10:14 Administrator@v1.0 创建
     **/
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> "";
    }
}

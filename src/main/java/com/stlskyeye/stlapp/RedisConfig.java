package com.stlskyeye.stlapp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

/**
 *redis 的配置文件，
 *    注意点： 多个redisTemplate 实现大方法不能一样。。
 *
 *
 *
 *   2018-01-20 zqh
 */

@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    /**
     * spring boot 自动配置 整合 redis
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory);
        setMySerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置序列化方法
     */
    private void setMySerializer(RedisTemplate template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

    /**
     *  spring boot 实现自己配置redis，可实现多redis实例
     * @param hostName
     * @param port
     * @param password
     * @param maxIdle
     * @param minIdle
     * @param maxWaitMillis
     * @param maxTotal
     * @return
     */
    @Bean
    public StringRedisTemplate  stringRedisTemplate(
            @Value("${my.log.redis.host}") String hostName,
            @Value("${my.log.redis.port}") int port,
            @Value("${my.log.redis.password}") String password,
            @Value("${my.log.redis.pool.max-idle}") int maxIdle,
            @Value("${my.log.redis.pool.min-idle}") int minIdle,
            @Value("${my.log.redis.pool.max-wait}") long maxWaitMillis,
            @Value("${my.log.redis.pool.max-active}") int maxTotal) {
        StringRedisTemplate  temple = new StringRedisTemplate ();
        temple.setConnectionFactory(connectionFactory(hostName, port, password,
                maxIdle, minIdle,  maxWaitMillis, maxTotal));
        //设置序列化
        setMySerializer(temple) ;
        return temple;
    }

    public RedisConnectionFactory connectionFactory(String hostName, int port,
                                                    String password, int maxIdle, int minIdle,
                                                    long maxWaitMillis, int maxTotal) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(hostName);
        jedis.setPort(port);
        if (!StringUtils.isEmpty(password)) {
            jedis.setPassword(password);
        }
        jedis.setPoolConfig(poolCofig(maxIdle, minIdle, maxWaitMillis,
                maxTotal));
        // 初始化连接pool
        jedis.afterPropertiesSet();
        RedisConnectionFactory factory = jedis;
        return factory;
    }

    public JedisPoolConfig poolCofig(int maxIdle, int minIdle,
                                     long maxWaitMillis, int maxTotal) {
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        //最大空闲连接数
        poolCofig.setMaxIdle(maxIdle);
        poolCofig.setMinIdle(minIdle);
        poolCofig.setMaxWaitMillis(maxWaitMillis);
        poolCofig.setMaxTotal(maxTotal);
        return poolCofig;
    }
}

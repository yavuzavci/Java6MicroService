package com.yavuz.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {
    @Value("${userservice.redis.host}")
    private String redisHost;
    @Value("${userservice.redis.port}")
    private Integer redisPort;
    @Bean
    public LettuceConnectionFactory redisEBaglan(){
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(redisHost,redisPort)
        );
    }
}

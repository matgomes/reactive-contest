package com.reactivecontest.ranking.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class Config {

    @Bean
    public Mapper mapper(){
        return DozerBeanMapperBuilder.buildDefault();
    }

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplateString(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }

    @Bean
    public ReactiveZSetOperations zSetOps(@Qualifier("reactiveRedisTemplateString") final ReactiveRedisTemplate template) {
        return template.opsForZSet();
    }

}

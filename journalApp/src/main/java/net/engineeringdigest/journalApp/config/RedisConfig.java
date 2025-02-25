package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        //basically we are doing this because if we will pass something from the springboot then we will not be able to see it directly through the redis and if we set anything in the redis
        // directly we won't be able to see it from the springboot app so to reduce this we use this serializing concept
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;


    }
}

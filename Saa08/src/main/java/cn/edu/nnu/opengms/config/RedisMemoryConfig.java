package cn.edu.nnu.opengms.config;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.alibaba.cloud.ai.graph.checkpoint.savers.redis.RedisSaver;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RedisMemoryConfig {
    private static final String REDIS_ADDRESS = "redis://127.0.0.1:6379";
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDIS_ADDRESS)
                .setDatabase(0)
                .setTimeout((int) Duration.ofSeconds(2).toMillis())
                .setConnectTimeout((int) Duration.ofSeconds(3).toMillis());
        return Redisson.create(config);
    }

    @Bean(name = "memoryAgent")
    public ReactAgent memoryAgent(ChatModel deepseek) {
        return ReactAgent.builder()
                .name("memory-agent")
                .model(deepseek)
                .saver(new MemorySaver())
                .build();
    }

    @Bean(name = "redisMemoryAgent")
    public ReactAgent redisMemoryAgent(ChatModel deepseek, RedissonClient redissonClient) {
        RedisSaver redisSaver = RedisSaver.builder()
                .redisson(redissonClient)
                .build();

        return ReactAgent.builder()
                .name("redis-memory-agent")
                .model(deepseek)
                .saver(redisSaver)
                .build();
    }
}

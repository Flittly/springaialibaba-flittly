package cn.edu.nnu.opengms.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder()
                    .apiKey(apiKey)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatModel dashscopeChatModel) {
        return ChatClient.builder(dashscopeChatModel).build();
    }
}

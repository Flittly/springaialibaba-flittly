package cn.edu.nnu.opengms.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    private static final String DEEPSEEK_MODEL = "deepseek-v3";

    @Bean(name = "deepseek")
    public ChatModel deepseek(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder()
                        .apiKey(apiKey).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }

    @Bean(name  = "deepseekChatClient")
    public ChatClient deepseekChatClient(@Qualifier("deepseek") ChatModel deepSeek)
    {
        return ChatClient.builder(deepSeek)
                .defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())
                .build();
    }
}

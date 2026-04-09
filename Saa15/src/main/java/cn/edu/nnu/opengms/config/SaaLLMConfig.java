package cn.edu.nnu.opengms.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SaaLLMConfig {

    @Bean("mcpChatClient")
    public ChatClient mcpChatClient(
            ChatModel chatModel,
            ToolCallbackProvider tools)
    {

        return ChatClient.builder(chatModel)
                .defaultToolCallbacks(tools.getToolCallbacks())  //mcp协议，配置见yml文件
                .build();
    }
}
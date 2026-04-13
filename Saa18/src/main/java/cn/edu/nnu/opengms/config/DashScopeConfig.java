package cn.edu.nnu.opengms.config;

import cn.edu.nnu.opengms.service.MenuTools;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

@Configuration
public class DashScopeConfig {
    @Bean
    public ReactAgent menuAgent(ChatModel chatModel, MenuTools menuTools, ToolCallbackProvider mcpTools)
    {
        ToolCallback[] localTools = ToolCallbacks.from(menuTools);
        ToolCallback[] externalTools = mcpTools.getToolCallbacks();

        ToolCallback[] allTools = Stream.concat(Arrays.stream(localTools), Arrays.stream(externalTools))
                .toArray(ToolCallback[]::new);

        return ReactAgent.builder()
                .name("menu-agent")
                .description("根据用户问题推荐菜单、解释菜品或查询天气")
                .model(chatModel)
                .tools(allTools)
                .build();
    }
}

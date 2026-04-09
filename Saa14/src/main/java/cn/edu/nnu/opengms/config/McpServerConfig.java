package cn.edu.nnu.opengms.config;


import cn.edu.nnu.opengms.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP Server 配置
 * 作用：把本地业务能力注册为 MCP 对外服务能力
 */
@Configuration
public class McpServerConfig
{
    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService)
    {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }
}
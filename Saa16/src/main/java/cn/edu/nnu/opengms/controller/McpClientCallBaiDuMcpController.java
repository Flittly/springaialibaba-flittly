package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class McpClientCallBaiDuMcpController {
    @Resource
    private ChatClient chatClient;

    @Resource
    private ChatModel chatModel;

    @GetMapping("/mcp/chat")
    public Flux<String> chat(String msg)
    {
        return chatClient.prompt(msg).stream().content();
    }

    @RequestMapping("/mcp/chat2")
    public Flux<String> chat2(String msg)
    {
        return chatModel.stream(msg);
    }
}

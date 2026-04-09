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
public class McpClientController {
    @Resource
    private ChatClient chatClient;

    @Resource
    private ChatModel chatModel;

    @GetMapping("/mcpclient/chat")
    public Flux<String> chat(@RequestParam(name = "msg", defaultValue = "北京") String msg)
    {
        System.out.println("使用了mcp");
        return chatClient.prompt(msg).stream().content();
    }

    @RequestMapping("/mcpclient/chat2")
    public Flux<String> chat2(@RequestParam(name = "msg", defaultValue = "北京") String msg)
    {
        System.out.println("未使用mcp");
        return chatModel.stream(msg);
    }
}


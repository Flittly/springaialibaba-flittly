package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamOutputController {
    //v1 通过ChatModel实现stream实现流式输出
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @GetMapping(value = "/stream/chatflux1")
    public Flux<String> chatflux(@RequestParam(name = "question", defaultValue = "你是谁") String question)
    {
        return deepseekChatModel.stream(question);
    }

    //v2 通过ChatClient实现stream实现流式输出
    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @GetMapping("/stream/chatflux2")
    public Flux<String> chatflux2(@RequestParam(name = "question", defaultValue = "你是谁") String question)
    {
        return deepseekChatClient.prompt(question).stream().content();
    }
}

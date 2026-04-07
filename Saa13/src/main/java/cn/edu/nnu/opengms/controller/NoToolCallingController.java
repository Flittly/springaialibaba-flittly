package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

public class NoToolCallingController {
    @Resource(name = "deepseek")
    private ChatModel chatModel;

    @GetMapping("/notoolcall/chat")
    public Flux<String> chat(@RequestParam(name = "msg", defaultValue = "你是谁现在几点") String msg)
    {
        return chatModel.stream(msg);
    }
}

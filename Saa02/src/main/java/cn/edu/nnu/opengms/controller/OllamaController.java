package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OllamaController {

    @Resource
    @Qualifier("ollamaChatModel")
    private ChatModel chatmodel;

    @GetMapping("/ollama/chat")
    public String chat(@RequestParam(name = "msg") String msg) {
        String result = chatmodel.call(msg);
        System.out.println("---结果：" + result);
        return result;
    }

    @GetMapping("/ollama/streamchat")
    public Flux<String> streamchat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return chatmodel.stream(msg);
    }
}

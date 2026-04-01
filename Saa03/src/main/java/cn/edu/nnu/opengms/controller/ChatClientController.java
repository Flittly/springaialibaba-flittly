package cn.edu.nnu.opengms.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientController {
    private final ChatClient dashScopeChatClient;

    public ChatClientController(ChatModel dashScopeChatModel){
        this.dashScopeChatClient = ChatClient.builder(dashScopeChatModel).build();
    }

    @GetMapping("/chatclient/hello")
    public String hello(String msg){
        String result = dashScopeChatClient.prompt()
                .user(msg)
                .call()
                .content();
        return result;
    }
}

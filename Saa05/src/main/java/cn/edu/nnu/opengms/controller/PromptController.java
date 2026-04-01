package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PromptController {
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @GetMapping("/prompt/chat")
    public Flux<String> chat(String question) {
        return deepseekChatClient.prompt()
                .system("你是一个法律助手，只回答法律问题，其它问题回复，我只能回答法律相关问题，其它无可奉告")
                .user(question)
                .stream()
                .content();
    }

    @GetMapping("/prompt/chat2")
    public Flux<ChatResponse> chat2(String question) {
        //系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个法律助手，只回答法律问题，其它问题回复，我只能回答法律相关问题，其它无可奉告");

        //用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(systemMessage, userMessage);

        return deepseekChatModel.stream(prompt);
    }
}

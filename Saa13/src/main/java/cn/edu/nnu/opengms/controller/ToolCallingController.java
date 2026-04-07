package cn.edu.nnu.opengms.controller;

import cn.edu.nnu.opengms.utils.DateTimeTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ToolCallingController {
    @Resource(name = "deepseek")
    private ChatModel chatModel;
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/toolcall/chat")
    public String chat(@RequestParam(name = "msg", defaultValue = "你是谁现在几点") String msg)
    {
        //1、工具注册到工具集合里面
        ToolCallback[] tools = ToolCallbacks.from(new DateTimeTools());

        //2、将工具集配置进ChatOptions对象
        ChatOptions options = ToolCallingChatOptions.builder().toolCallbacks(tools).build();

        //3、构建提示词
        Prompt prompt = new Prompt(msg, options);

        //4、调用大模型
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @GetMapping("/toolcall/chat2")
    public Flux<String> chat2(@RequestParam(name = "msg", defaultValue = "你是谁现在几点") String msg)
    {
        return chatClient.prompt(msg)
                .tools(new DateTimeTools())
                .stream()
                .content();
    }
}

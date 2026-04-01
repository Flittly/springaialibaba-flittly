package cn.edu.nnu.opengms.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class PromptTemplateController {
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @GetMapping("/prompttemplate/chat")
    public Flux<String> chat(
            @RequestParam("topic") String topic,
            @RequestParam("output_format") String output_format,
            @RequestParam("wordCount") String wordCount) {
        // 1. 在代码中定义模板字符串
        PromptTemplate promptTemplate = new PromptTemplate(
                "讲一个关于{topic}的故事，" +
                        "并以{output_format}格式输出，" +
                        "字数在{wordCount}左右"
        );

        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,              // 替换 {topic}
                "output_format", output_format, // 替换 {output_format}
                "wordCount", wordCount       // 替换 {wordCount}
        ));

        return deepseekChatClient.prompt(prompt).stream().content();
    }

    @Value("classpath:/prompttemplate/atguigu-template.txt")
    private org.springframework.core.io.Resource userTemplate;

    @GetMapping("/prompttemplate/chat2")
    public String chat2(@RequestParam("topic") String topic,
                        @RequestParam("output_format") String output_format,
                        @RequestParam("wordCount") String wordCount) {
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);

        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "output_format", output_format,
                "wordCount", wordCount
        ));

        return deepseekChatClient.prompt(prompt).call().content();
    }

    @GetMapping("/prompttemplate/chat3")
    public String chat3(@RequestParam("topic") String topic, @RequestParam("userTopic") String userTopic) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答{systemTopic}其它无可奉告，以HTML格式的结果。");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of(
                "systemTopic", topic
        ));

        PromptTemplate userPromptTemplate = new PromptTemplate("请用HTML格式回答：{userTopic}");
        Prompt userPrompt = userPromptTemplate.create(Map.of(
                "userTopic", userTopic
        ));

        Message userMessage = userPrompt.getInstructions().get(0);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return deepseekChatClient.prompt(prompt).call().content();
    }

    @GetMapping("/prompttemplate/chat4")
    public String chat4(@RequestParam("question") String question) {
        SystemMessage systemMessage = new SystemMessage("你是一个Java编程助手，拒绝回答非技术问题。");

        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        String result = deepseekChatModel.call(prompt).getResult().getOutput().getText();
        System.out.println(result);
        return result;
    }

    @GetMapping("/prompttemplate/chat5")
    public Flux<String> chat5(@RequestParam("question") String question) {
        return deepseekChatClient.prompt()
                .system("你是一个Java编程助手，拒绝回答非技术问题。")
                .user(question)
                .stream()
                .content();
    }
}


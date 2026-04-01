package cn.edu.nnu.opengms.controller;

import cn.edu.nnu.opengms.records.StudentRecord;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class StructuredOutputController {
    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @GetMapping("/structuredoutput/chat")
    public StudentRecord chat(@RequestParam(name = "sname") String sname,
                              @RequestParam(name = "email") String email)
    {
        return deepseekChatClient.prompt().user(
                new Consumer<ChatClient.PromptUserSpec>() {
                    @Override
                    public void accept(ChatClient.PromptUserSpec promptUserSpec) {
                        promptUserSpec.text("学号1001，我叫{sname},大学专业计算机科学与技术,邮箱{email}")
                                .param("sname", sname)
                                .param("email", email);
                    }
                }
        ).call().entity(StudentRecord.class);
    }

    @GetMapping("/structuredoutput/chat2")
    public StudentRecord chat2(@RequestParam(name = "sname")String sname,
                               @RequestParam(name = "email")String email)
    {
        return deepseekChatClient.prompt().user(
                promptUserSpec -> promptUserSpec.text("学号1001，我叫{sname},大学专业计算机科学与技术,邮箱{email}")
                        .param("sname", sname)
                        .param("email", email)
        ).call().entity(StudentRecord.class);
    }
}

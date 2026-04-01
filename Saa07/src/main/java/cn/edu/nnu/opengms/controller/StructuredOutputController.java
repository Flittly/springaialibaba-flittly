package cn.edu.nnu.opengms.controller;

import cn.edu.nnu.opengms.records.StudentRecord;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StructuredOutputController {
    @Resource(name = "deepseek")
    private ChatClient deepseekChatClient;

    @GetMapping("/structuredoutput/chat")
    public StudentRecord chat(@RequestParam(name = "sname") String sname,
                              @RequestParam(name = "email") String email)
    {
        return deepseekChatClient.prompt().user(
                new
        )
    }
}
